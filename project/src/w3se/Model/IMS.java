package w3se.Model;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import w3se.Base.Book;
import w3se.Base.BookGenres;
import w3se.Base.LogItem;
import w3se.Base.LogItemFactory;
import w3se.Base.User;
import w3se.Model.Database.DatabaseAdaptor;
import w3se.Model.Database.UsersDB;

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
	
	private Configurations m_config;
	private BookGenres m_genres = new BookGenres();
	private boolean m_showDialogs = true;
	
	public static final int NAN = -1;
	public static final int KEYWORD_S = 0;
	public static final int BROWSE_S = 1;
	public static final int ADD_EDIT = 2;
	public static final int SELL = 3;
	public static final int LOGIN = 0;
	public static final int BOOKS = 1;

	/**
	 * constructor
	 */
	private IMS()
	{
		m_config = Configurations.getConfigFromFile("config");
		m_showDialogs = Boolean.parseBoolean(m_config.getValue("ErrorDialog"));
		m_genres.mergeFromFile(m_config.getValue("GenreListLoc"));
		m_scheduler.addObserver(this);
		m_dbAdaptor = new DatabaseAdaptor(m_config.getValue("ServerMode"), m_config);
		
	}
	
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
	public void login() throws Exception
	{
		m_dbAdaptor.setState(DatabaseAdaptor.USERS_DB);
		try
		{
			m_dbAdaptor.retrieve(new String[] {UsersDB.USERNAME, m_currentUser.getUsername()});
		}
		catch (Exception e)
		{
			addLog(LogItemFactory.createLogItem(new Date().toString(), LogItem.LOGIN, "Login failure."));
			throw new Exception("Login Failed: Username or password incorrect");
		}
		
		User user = (User)m_dbAdaptor.getResult();
		
		if (m_currentUser.getUsername().equals(user.getUsername()) && m_currentUser.getPassword().equals(user.getPassword()))
		{
			m_state = States.LOGGED_IN;
			m_currentUser = user;
		}
		else
		{
			m_state = States.LOGGED_OUT;
			addLog(LogItemFactory.createLogItem(new Date().toString(), LogItem.LOGIN, "Login failure."));
			throw new Exception("Login Failed: Username or password incorrect");
			
			
		}
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
		catch (SQLException e)
		{
			addLog(LogItemFactory.createLogItem(new Date().toString(), LogItem.SYSTEM, "Unable to create user."));
		}
		
		return false;
	}
	
	/**
	 * 
	 */
	public void shutdownSystem()
	{
		System.out.println("Exiting.....");
		m_dbAdaptor.close();
		m_genres.writeToFile();
		m_config.writeToFile();
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
	
	public User retreiveUser(String[] userName)
	{
		m_dbAdaptor.setState(DatabaseAdaptor.USERS_DB);
		User user = new User();
		
		try
		{
			m_dbAdaptor.retrieve(userName);
			user = (User)m_dbAdaptor.getResult();
		} 
		catch (Exception e)
		{
			addLog(LogItemFactory.createLogItem(new Date().toString(), LogItem.SYSTEM, "Unable to find user."));
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
	 * @return
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
					JOptionPane.showMessageDialog(null, e.getMessage(), "Privilege Error", JOptionPane.ERROR_MESSAGE);
				addLog(LogItemFactory.createLogItem(new Date().toString(), LogItem.SYSTEM, "Privilege Authenication Failure."));
			}
			setChanged();
			notifyObservers();
		}
	}
	
	/**
	 * method to find a requested book
	 * @param searchTerm
	 */
	@SuppressWarnings("unchecked")
	public void findBook(final String[] searchTerm)
	{
		m_dbAdaptor.setState(DatabaseAdaptor.BOOK_DB);
		try
		{
			m_dbAdaptor.retrieve(searchTerm);
			m_foundBookList = (ArrayList<Book>)m_dbAdaptor.getResult();
			
			if (m_foundBookList.size() > 0)
				setCurrentBook(m_foundBookList.get(0));
	
			else if (m_currentUser.getPrivilege() > User.GENERAL)
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
									addLog(LogItemFactory.createLogItem(new Date().toString(), LogItem.INVENTORY, "Unable to find book."));
								}
								
							}
						}));
			}
			else
			{
				if (m_showDialogs)
					JOptionPane.showMessageDialog(null, "Book not in inventory.", "Book not found.", JOptionPane.ERROR_MESSAGE);
				
				addLog(LogItemFactory.createLogItem(new Date().toString(), LogItem.INVENTORY, "Unable to find book."));
		
			}
		}
		catch (Exception e)
		{
			if (m_showDialogs)
				JOptionPane.showMessageDialog(null, e.getMessage(), "Book not found.", JOptionPane.ERROR_MESSAGE);
			addLog(LogItemFactory.createLogItem(new Date().toString(), LogItem.INVENTORY, "Unable to find book."));
		}
	}
	
	public ArrayList<Book> getListOfFoundBooks()
	{
		return m_foundBookList;
	}
	
	/**
	 * method to get the current book of the system
	 * @return
	 */
	public Book getCurrentBook()
	{
		return m_currentBook;
	}
	
	public void setListOfFoundBooks(ArrayList<Book> books)
	{
		m_foundBookList = books;
	}
	
	public void addToSoldList(Book book)
	{
		m_soldBookList.add(book);
	}
	
	public ArrayList<Book> getListofSoldBooks()
	{
		return m_soldBookList;
	}
	
	public void removeFromSoldList(int index)
	{
		m_soldBookList.remove(index);
	}
	
	public void removeAllFromSoldList()
	{
		int size = m_soldBookList.size();
		for (int i = 0; i < size; i++)
		{
			m_soldBookList.remove(i);
		}
	}
	
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
	
	public Book selectBook(int index)
	{
		return m_foundBookList.get(index);
	}
	
	public void addCurrentBookToDB()
	{
		try
		{
			m_genres.mergeFromBook(m_currentBook);
			m_dbAdaptor.setState(DatabaseAdaptor.BOOK_DB);
			m_dbAdaptor.add(m_currentBook);
		} catch (SQLException e)
		{
			addLog(LogItemFactory.createLogItem(new Date().toString(), LogItem.SYSTEM, "Unable to add book to database."));
		}
	}
	
	public ArrayList<Book> getPrevViewedList()
	{
		return m_prevViewedList;
	}
	
	public void addNewGenresFromBook(Book book)
	{
		m_genres.mergeFromBook(book);
	}
	
	public BookGenres getGenres()
	{
		return m_genres;
	}
	
	public void setVolatileUser(User user)
	{
		m_volatileUser = user;
	}
	
	public User getVolatileUser()
	{
		return m_volatileUser;
	}
	
	public void addToRemoveUserList(User user)
	{
		m_removeUserList.add(user);
	}
	
	public void clearRemoveUserList()
	{
		m_removeUserList = new ArrayList<User>();
	}
	
	public ArrayList<User> getRemoveUserList()
	{
		return m_removeUserList;
	}
	
	public void removeFromRemoveUserList(int index)
	{
		m_removeUserList.remove(index);
	}
	
	public void removeUserFromDB(User user)
	{
		m_dbAdaptor.setState(DatabaseAdaptor.USERS_DB);
		try
		{
			m_dbAdaptor.remove(user);
		} 
		catch (Exception e)
		{
			addLog(LogItemFactory.createLogItem(new Date().toString(), LogItem.SYSTEM, "Unable to remove user from database."));
		}
	}

	public DatabaseAdaptor getDatabaseAdaptor()
	{
		return m_dbAdaptor;
	}
	
	public Configurations getConfigurations()
	{
		return m_config;
	}
	
	public boolean showDialogs()
	{
		return m_showDialogs;
	}
	
	public void addLog(LogItem logItem)
	{
		m_dbAdaptor.setState(DatabaseAdaptor.LOGS_DB);
		try
		{
			m_dbAdaptor.add(logItem);
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public ArrayList<LogItem> getLogs(int actionType)
	{
		m_dbAdaptor.setState(DatabaseAdaptor.LOGS_DB);
		ArrayList<LogItem> items = new ArrayList<LogItem>();
		
		LogItem item = new LogItem();
		item.setAction(actionType);
		
		try
		{
			m_dbAdaptor.retrieve(item);
			items = (ArrayList<LogItem>)m_dbAdaptor.getResult();
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return items;
	}
}
