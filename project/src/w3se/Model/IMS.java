package w3se.Model;


import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import w3se.Model.Base.Book;
import w3se.Model.Base.BookGenres;
import w3se.Model.Base.LogItem;
import w3se.Model.Base.LogItemFactory;
import w3se.Model.Base.Receipt;
import w3se.Model.Base.States;
import w3se.Model.Base.Theme;
import w3se.Model.Base.User;
import w3se.Model.Database.BookDB;
import w3se.Model.Database.DatabaseAdaptor;
import w3se.Model.Database.LogsDB;
import w3se.Model.Database.UsersDB;
import w3se.View.SplashScreen;

public class IMS extends Observable implements Observer
{
	private static IMS m_instance = null;
	private States m_state = States.LOGGED_OUT;
	private User m_currentUser = new User(-1, User.GENERAL, "General", "General");
	private User m_volatileUser = new User(-1, User.GENERAL, "", "");
	private TaskManager m_scheduler = new TaskManager();
	private DatabaseAdaptor m_dbAdaptor = null;
	private Book m_currentBook = new Book();
	private ArrayList<Book> m_foundBookList = new ArrayList<Book>();
	private ArrayList<Book> m_soldBookList = new ArrayList<Book>();
	private ArrayList<Book> m_prevViewedList = new ArrayList<Book>();
	private ArrayList<User> m_removeUserList = new ArrayList<User>();
	private ArrayList<LogItem> m_logList = new ArrayList<LogItem>();
	private Exporter m_exporter;
	private Configurations m_config;
	private BookGenres m_genres = new BookGenres();
	private boolean m_showDialogs = true;
	private boolean m_showNotifySMode = true;
	private Theme m_theme;
	private Receipt m_receipt;
	private Package m_resources;
	
	/**
	 * constructor
	 */
	private IMS()
	{
	}
	
	private void setResourcePKGKey()
	{
		String hash = null;
		String key = getClass().getCanonicalName()+getClass().getModifiers();
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(key.getBytes());
			
			byte hashData[] = md.digest();
			
			Formatter formatter = new Formatter();
			
			for (int i = 0; i < hashData.length; i++)
			{
				formatter.format("%02x", hashData[i]);
			}
			
			hash = formatter.toString();
		} catch (NoSuchAlgorithmException e)
		{
			System.out.println("Password hashing failed");
		}	
		
		m_resources.setKey(hash);
	}
	
	public void fixResource(int type)
	{
		// unpack the resources first and foremost 
		m_resources = new Package(Configurations.RESOURCES_S, Configurations.RESOURCES_D, "");
		setResourcePKGKey();
		
		if (type == 0)
			m_resources.unpack();
		
		else
			m_resources.pack();
	}
	public void init()
	{
		// unpack the resources first and foremost 
		m_resources = new Package(Configurations.RESOURCES_S, Configurations.RESOURCES_D, "");
		setResourcePKGKey();
		m_resources.unpack();

		SplashScreen splash = new SplashScreen(new File(Configurations.W3SE_LOGO).getAbsolutePath());
		splash.run();

		m_config = Configurations.getConfigFromFile(new File(Configurations.CONFIG_LOC).getAbsolutePath());
		m_showDialogs = Boolean.parseBoolean(m_config.getValue("ErrorDialog"));
		m_showNotifySMode = Boolean.parseBoolean(m_config.getValue("NotifyOfSMode"));

		if (m_showNotifySMode)
			JOptionPane.showMessageDialog(null, "Running in "+ m_config.getValue("ServerMode")+ " mode.", "Mode of Operation", JOptionPane.INFORMATION_MESSAGE);

		m_theme = new Theme(new Color(Integer.parseInt(m_config.getValue("MainColor"))), new Color(Integer.parseInt(m_config.getValue("SecColor"))));

		m_receipt = new Receipt();
		m_receipt.setStoreName(m_config.getValue("BusinessName"));
		m_receipt.setStorePhoneNumber(m_config.getValue("BusinessPhoneNumber"));
		m_receipt.setMessageToRecipient(m_config.getValue("BusinessMessage"));
		m_receipt.setSlogan(m_config.getValue("BusinessSlogan"));

		m_genres.mergeFromFile(new File(m_config.getValue("GenreListLoc")).getAbsolutePath());
		m_scheduler.addObserver(this);
		m_dbAdaptor = new DatabaseAdaptor(m_config.getValue("ServerMode"), m_config);
	}
	
	/**
	 * gotta love singletons (GLOBAL CHAOS!)
	 * @return
	 */
	public static IMS getInstance()
	{
		if (m_instance == null)	
			m_instance = new IMS();
		
		return m_instance;
	}
	
	/**
	 * method to set the current user of the system
	 * @param user
	 */
	public void setUser(User user)
	{
		m_currentUser = user;
	}
	
	
	/**
	 * method to login
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void login() throws Exception
	{
		m_dbAdaptor.setState(DatabaseAdaptor.USERS_DB);
		try
		{
			m_dbAdaptor.retrieve(new String[] {UsersDB.USERNAME, m_currentUser.getUsername()});
		}
		catch (Exception e)
		{
			addLog(LogItemFactory.createLogItem(LogItem.LOGIN, "Login failure."));
			throw new Exception("Login Failed: Username or password incorrect");
		}
		
		ArrayList<User> userList = (ArrayList<User>)m_dbAdaptor.getResult();
		
		if (userList.size() > 0)
		{
			User user = userList.get(0);
			if (m_currentUser.getUsername().equals(user.getUsername()) && m_currentUser.getPassword().equals(user.getPassword()))
			{
				m_state = States.LOGGED_IN;
				m_currentUser = user;
			}
			else
			{
				m_state = States.LOGGED_OUT;
				addLog(LogItemFactory.createLogItem(LogItem.LOGIN, "Login failure."));
				throw new Exception("Login Failed: Username or password incorrect");
				
				
			}
		}
		else
			throw new Exception("Username or password incorrect");
	}
	
	/**
	 * method to logout
	 */
	public void logout()
	{
		m_state = States.LOGGED_OUT;
	}
	
	/**
	 * method to add a user to the system
	 * @param user
	 * @return
	 */
	public boolean createUser(User user)
	{
		m_dbAdaptor.setState(DatabaseAdaptor.USERS_DB);
		
		try
		{
			m_dbAdaptor.add(user);
		}
		catch (Exception e)
		{
			addLog(LogItemFactory.createLogItem(LogItem.SYSTEM, "Unable to create user."));
		}
		
		return false;
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	public void shutdownSystem() throws Exception
	{
		System.out.println("Exiting.....");
		logout();
		addLog(LogItemFactory.createLogItem(LogItem.LOGIN, getCurrentUser().getUsername()+" logged out of the system."));
		m_dbAdaptor.close();
		m_genres.writeToFile();
		m_config.writeToFile();
		m_resources.pack();
		m_resources.close();
	}
	
	/**
	 * 
	 * @return
	 */
	public States getLoginState()
	{
		return m_state;
	}
	
	/**
	 * method to get a stripped copy of the user
	 * @return
	 */
	public User getCurrentUser()
	{
		User copy = new User(m_currentUser.getUID(), m_currentUser.getPrivilege(), m_currentUser.getUsername(), m_currentUser.getPassword());
		return copy;
	}
	
	/**
	 * method to retreive a user 
	 * @param term String[] - first index is the query type (UserDB.U_ID or UserDB.USERNAME) second index holds the id 
	 * or the username
	 * @return ArrayList<User> - found users matching the criteria
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<User> retreiveUser(String[] term)
	{
		m_dbAdaptor.setState(DatabaseAdaptor.USERS_DB);
		ArrayList<User> user = new ArrayList<User>();
		
		try
		{
			m_dbAdaptor.retrieve(term);
			user = (ArrayList<User>)m_dbAdaptor.getResult();
		} 
		catch (Exception e)
		{
			addLog(LogItemFactory.createLogItem(LogItem.SYSTEM, "Unable to find user."));
		}
		
		return user;
	}
	
	/**
	 * add a view to the system
	 * @param obs
	 */
	public void addView(Observer obs)
	{
		addObserver(obs);
		setChanged();
	}
	
	/**
	 * method to get the task manager
	 * @return TaskManager - reference to the task manager of the system
	 */
	public TaskManager getTaskManager()
	{
		return m_scheduler;
	}
	
	/**
	 * 
	 */
	@Override
	public void update(Observable sender, Object obj)
	{
		if (obj != null && obj.equals("task_added"))
		{
			try
			{
				m_scheduler.runTask();
			}
			catch (Exception e)
			{
				if (m_showDialogs)
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				addLog(LogItemFactory.createLogItem(LogItem.SYSTEM, "Privilege Authenication Failure."));
			}
			setChanged();
			notifyObservers();
		}
	}
	
	/**
	 * method to find a requested book
	 * @param searchTerm String[] - first index is query type (BookDB.KEYWORD or BookDB.BROWSE) second index is the actual
	 * searchable term
	 */
	@SuppressWarnings("unchecked")
	public void findBook(final String[] searchTerm)
	{
		m_dbAdaptor.setState(DatabaseAdaptor.BOOK_DB);
		try
		{
			m_dbAdaptor.retrieve(searchTerm);
			m_foundBookList = (ArrayList<Book>)m_dbAdaptor.getResult();
			
			if (searchTerm[0] == BookDB.ONLINE && m_foundBookList.size() <= 0)
			{
				if (m_currentUser.getPrivilege() > User.GENERAL)
				{
					m_scheduler.addTask(new Task(User.WORKER, new 
							Runnable()
							{	
								public void run()
								{
									m_dbAdaptor.setState(DatabaseAdaptor.ONLINE_DB);
									try
									{
										m_dbAdaptor.retrieve(searchTerm[1]);
										m_foundBookList = (ArrayList<Book>)m_dbAdaptor.getResult();
										if (m_foundBookList.size() > 0)
											m_currentBook = m_foundBookList.get(0);
									} 
									catch (Exception e)
									{
										if (m_showDialogs)
											JOptionPane.showMessageDialog(null, e.getMessage(), "Book not found.", JOptionPane.ERROR_MESSAGE);
										
										addLog(LogItemFactory.createLogItem(LogItem.INVENTORY, "Unable to find book."));
									}
									
								}
							}));
				}
			}
			if (m_foundBookList.size() <= 0)
			{
				if (m_showDialogs)
					JOptionPane.showMessageDialog(null, "Book not in inventory.", "Book not found.", JOptionPane.ERROR_MESSAGE);
				addLog(LogItemFactory.createLogItem(LogItem.INVENTORY, "Unable to find book."));
		
			}
		}
		catch (Exception e)
		{
			if (m_showDialogs)
				JOptionPane.showMessageDialog(null, e.getMessage(), "Book not found.", JOptionPane.ERROR_MESSAGE);
			addLog(LogItemFactory.createLogItem(LogItem.INVENTORY, "Unable to find book."));
		}
	}
	
	public void findBookOnline(final String isbn)
	{
		if (m_currentUser.getPrivilege() > User.GENERAL)
		{
			m_scheduler.addTask(new Task(User.WORKER, new 
					Runnable()
					{	
						public void run()
						{
							m_dbAdaptor.setState(DatabaseAdaptor.ONLINE_DB);
							try
							{
								m_dbAdaptor.retrieve(isbn);
								m_foundBookList = (ArrayList<Book>)m_dbAdaptor.getResult();
								if (m_foundBookList.size() > 0)
									m_currentBook = m_foundBookList.get(0);
							} 
							catch (Exception e)
							{
								if (m_showDialogs)
									JOptionPane.showMessageDialog(null, e.getMessage(), "Book not found.", JOptionPane.ERROR_MESSAGE);
								
								addLog(LogItemFactory.createLogItem(LogItem.INVENTORY, "Unable to find book."));
							}
							
						}
					}));
		}
	}
	
	public void removeBookFromDB(Book book)
	{
		try
		{
			m_dbAdaptor.remove(book);
		} catch (Exception e)
		{
			if (m_showDialogs)
				JOptionPane.showMessageDialog(null, "Book removal error", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * method to get the list of found books from the system (found books are the result of findBook(String[] searchTerm)
	 * @return ArrayList<Book> - list of found books from the system
	 */
	public ArrayList<Book> getListOfFoundBooks()
	{
		return m_foundBookList;
	}
	
	/**
	 * method to get the current book of the system
	 * @return Book - get the current active book in the system
	 */
	public Book getCurrentBook()
	{
		return m_currentBook;
	}
	
	/**
	 * method to set the list of found books
	 * @param books
	 */
	public void setListOfFoundBooks(ArrayList<Book> books)
	{
		m_foundBookList = books;
	}
	
	/**
	 * method to add a book to the sold list of the system
	 * @param book
	 */
	public void addToSoldList(Book book)
	{
		m_soldBookList.add(book);
	}
	
	/**
	 * method to get the list of sold books from the system
	 * @return
	 */
	public ArrayList<Book> getListofSoldBooks()
	{
		return m_soldBookList;
	}
	
	/**
	 * method to remove a particular book from the sold list of a system
	 * @param index
	 */
	public void removeFromSoldList(int index)
	{
		m_soldBookList.remove(index);
	}
	
	/**
	 * method to purge the sold list
	 */
	public void setListOfSoldBooks(ArrayList<Book> list)
	{
		m_soldBookList = list;
	}
	
	/**
	 * method to finalize the sell of all the books in the sold list (decrement the quantity of books and log the sell)
	 */
	public void finalizeSell()
	{
		for (int i = 0; i < m_soldBookList.size(); i++)
		{
			Book book = m_soldBookList.get(i);
			book.setQuantity(book.getQuantity() - 1);
			
			setCurrentBook(book);
			addCurrentBookToDB();
		}
	}
	
	/**
	 * method to set the current book of the system
	 * @param book
	 */
	public void setCurrentBook(Book book)
	{
		//m_prevViewedList.add(book);
		m_currentBook = book;
	}
	
	/**
	 * method to select a particular book from the found book list
	 * @param index
	 * @return
	 */
	public Book selectBook(int index)
	{
		return m_foundBookList.get(index);
	}
	
	/**
	 * method to insert the current active book of the system into the book database
	 */
	public void addCurrentBookToDB()
	{
		try
		{
			m_genres.mergeFromBook(m_currentBook);
			m_dbAdaptor.setState(DatabaseAdaptor.BOOK_DB);
			m_dbAdaptor.add(m_currentBook);
		} catch (Exception e)
		{
			addLog(LogItemFactory.createLogItem(LogItem.SYSTEM, "Unable to add book to database."));
		}
	}
	
	
	// uncomment later
	/*public ArrayList<Book> getPrevViewedList()
	{
		return m_prevViewedList;
	}*/
	
	public void setReceipt(Receipt receipt)
	{
		m_receipt = receipt;
	}
	
	public Receipt getReceipt()
	{
		return m_receipt;
	}
	
	/**
	 * method to merge the genres from a book with the main genres list of the system
	 * @param book
	 */
	public void addNewGenresFromBook(Book book)
	{
		m_genres.mergeFromBook(book);
	}
	
	/**
	 * method to get the genres list of the system
	 * @return BookGenres
	 */
	public BookGenres getGenres()
	{
		return m_genres;
	}
	
	/**
	 * method to set the mutable user of the system (used for editing a previous user) 
	 * @param user
	 */
	public void setVolatileUser(User user)
	{
		m_volatileUser = user;
	}
	
	/**
	 * method to get the current mutable user of the system
	 * @return
	 */
	public User getVolatileUser()
	{
		return m_volatileUser;
	}
	
	/**
	 * method to print the receipt of from the sold book list
	 */
	public void printReceipt()
	{
		m_receipt.printReceipt(m_soldBookList);
	}
	
	/**
	 * method to add a user to the dispose list of the system
	 * @param user
	 */
	public void addToRemoveUserList(User user)
	{
		m_removeUserList.add(user);
	}
	
	/**
	 * method to purge the dispose list without removing user from the database
	 */
	public void clearRemoveUserList()
	{
		m_removeUserList = new ArrayList<User>();
	}
	
	/**
	 * method to get the dispose list of the system
	 * @return
	 */
	public ArrayList<User> getRemoveUserList()
	{
		return m_removeUserList;
	}
	
	/**
	 * method to remove a particular user from the dispose list
	 * @param index
	 */
	public void removeFromRemoveUserList(int index)
	{
		m_removeUserList.remove(index);
	}
	
	/**
	 * method to remove a particular user from the user database
	 * @param user
	 */
	public void removeUserFromDB(User user)
	{
		m_dbAdaptor.setState(DatabaseAdaptor.USERS_DB);
		try
		{
			m_dbAdaptor.remove(user);
		} 
		catch (Exception e)
		{
			addLog(LogItemFactory.createLogItem(LogItem.SYSTEM, "Unable to remove user from database."));
		}
	}

	/**
	 * method to get the database adaptor of the system
	 * @return DatabaseAdaptor
	 */
	public DatabaseAdaptor getDatabaseAdaptor()
	{
		return m_dbAdaptor;
	}
	
	/**
	 * method to get the system configurations
	 * @return Configurations
	 */
	public Configurations getConfigurations()
	{
		return m_config;
	}
	
	/**
	 * method to see if the system is set to show dialogs
	 * @return
	 */
	public boolean showDialogs()
	{
		return m_showDialogs;
	}
	
	public boolean showNotifyOfServerMode()
	{
		return m_showNotifySMode;
	}
	
	public void toggleDialog(boolean isEnabled)
	{
		m_showDialogs = isEnabled;
	}
	
	public void toggleNotifySMode(boolean isEnabled)
	{
		m_showNotifySMode = isEnabled;
	}
	
	/**
	 * method to add a log entry to the log database
	 * @param logItem
	 */
	public void addLog(LogItem logItem)
	{
		m_dbAdaptor.setState(DatabaseAdaptor.LOGS_DB);
		try
		{
			m_dbAdaptor.add(logItem);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * method to get the log entries from the system
	 * @param actionType - LogItem.Action
	 * @return - list of log items
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<LogItem> getLogs(String[] term)
	{
		m_dbAdaptor.setState(DatabaseAdaptor.LOGS_DB);
		ArrayList<LogItem> items = new ArrayList<LogItem>();
	
		try
		{
			m_dbAdaptor.retrieve(term);
			items = (ArrayList<LogItem>)m_dbAdaptor.getResult();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return items;
	}
	
	public void removeLogs()
	{
		m_dbAdaptor.setState(DatabaseAdaptor.LOGS_DB);
		LogItem item = new LogItem();
		item.setAction(LogItem.ALL);
		try
		{
			m_dbAdaptor.remove(item);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * set the exporter for the system
	 * @param exporter
	 */
	public void setExporter(Exporter exporter)
	{
		m_exporter = exporter;
	}
	
	public void setTheme(Theme theme)
	{
		m_theme = theme;
	}
	
	public Theme getTheme()
	{
		return m_theme;
	}
	
	/**
	 * method to export a database to a particular file
	 * @param dbType - DatabaseAdaptor.BOOK_DB, DatabaseAdaptor.USERS_DB, DatabaseAdaptor.ONLINE_DB, DatabaseAdaptor.LOGS_DB
	 * @param filename
	 */
	@SuppressWarnings("rawtypes")
	public void export(int dbType, String filename)
	{
		m_exporter.setFilename(filename);
		ArrayList list;
		
		switch (dbType)
		{
			case DatabaseAdaptor.BOOK_DB:
				findBook(new String[] {BookDB.KEYWORD, ""});	// find all the books
				list = m_foundBookList;
				break;
			case DatabaseAdaptor.USERS_DB:
				list = (ArrayList<User>)retreiveUser(new String[] {UsersDB.USERNAME, ""});
				break;
			case DatabaseAdaptor.LOGS_DB:
				 list = getLogs(new String[] {LogsDB.ALL, LogsDB.TIME,""});
				break;
			default:
				list = new ArrayList();
				break;
		}
		
		try
		{
			m_exporter.export(list);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
