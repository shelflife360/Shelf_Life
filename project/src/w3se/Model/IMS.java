package w3se.Model;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;

import w3se.Base.Book;
import w3se.Base.BookGenres;
import w3se.Base.User;
import w3se.Model.Database.DatabaseAdaptor;
import w3se.View.SplashScreen;
import w3se.View.View;

public class IMS extends Observable implements Observer
{
	private States m_state = States.LOGGED_OUT;
	private User m_currentUser = new User(-1, User.GENERAL, "General", "General");
	private static View m_view = null;
	private TaskManager m_scheduler = new TaskManager();
	private static IMS m_instance = null;
	private DatabaseAdaptor m_dbAdaptor = new DatabaseAdaptor();
	private Book m_currentBook = new Book();
	private ArrayList<Book> m_bookList = new ArrayList<Book>();
	private BookGenres m_genres = new BookGenres();
	
	public static final int NAN = -1;
	public static final int KEYWORD_S = 0;
	public static final int BROWSE_S = 1;
	public static final int ADD_EDIT = 2;
	public static final int SELL = 3;
	private int m_state2 = NAN;
	
	/**
	 * constructor
	 */
	private IMS()
	{
		SplashScreen splash = new SplashScreen("logo.png");
		m_scheduler.addObserver(this);
		m_genres.importFromFile("genres.txt");
	}
	
	/**
	 * 
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
	public void login() throws Exception
	{
		m_dbAdaptor.setState(DatabaseAdaptor.USERS_DB);
		m_dbAdaptor.retrieve(m_currentUser.getUsername());
		User user = (User)m_dbAdaptor.getResult();
		
		if (m_currentUser.getUsername().equals(user.getUsername()) && m_currentUser.getPassword().equals(user.getPassword()))
		{
			m_state = States.LOGGED_IN;
			m_currentUser = user;
		}
		else
		{
			m_state = States.LOGGED_OUT;
			throw new Exception("username or password were incorrect");
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
		try
		{
			m_dbAdaptor.add(user);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 
	 */
	public void shutdownSystem()
	{
		try
		{
			m_dbAdaptor.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
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
	public User getUser()
	{
		User copy = new User(-1, m_currentUser.getPrivilege(), "DeadBeef", "DeadBeef");
		return copy;
	}
	
	/**
	 * add a view to the system
	 * @param obs
	 */
	public void addView(Observer obs)
	{
		addObserver(obs);
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
			m_scheduler.runTask();
			setChanged();
			notifyObservers(m_currentBook);
		}
	}
	
	/**
	 * method to find a requested book
	 * @param searchTerm
	 */
	public void findBook(final String searchTerm)
	{
		try
		{
			
			m_dbAdaptor.setState(DatabaseAdaptor.SHELF_LIFE_DB);
			m_dbAdaptor.retrieve("ISBN,"+searchTerm);
			m_bookList = (ArrayList<Book>)m_dbAdaptor.getResult();
			
			if (m_state2 == ADD_EDIT && m_bookList.get(0).getISBN().equals(""))
			{
				m_scheduler.addTask(new Task(User.WORKER, new 
						Runnable()
						{	
							public void run()
							{
								m_dbAdaptor.setState(DatabaseAdaptor.ONLINE_DB);
								try
								{
									m_dbAdaptor.retrieve("ISBN,"+searchTerm);
									m_currentBook = (Book)m_dbAdaptor.getResult();
								} catch (Exception e)
								{
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							}
						}));
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public ArrayList<Book> getListOfBooks()
	{
		return m_bookList;
	}
	
	/**
	 * method to get the current book of the system
	 * @return
	 */
	public Book getCurrentBook()
	{
		return m_currentBook;
	}
	
	public void setListOfBooks(ArrayList<Book> books)
	{
		m_bookList = books;
	}
	/**
	 * method to set the current book of the system
	 * @param book
	 */
	public void setCurrentBook(Book book)
	{
		m_currentBook = book;
	}
	
	public void selectBook(int index)
	{
		m_currentBook = m_bookList.get(index);
	}
	public void addCurrentBookToDB()
	{
		try
		{
			m_dbAdaptor.setState(DatabaseAdaptor.SHELF_LIFE_DB);
			m_dbAdaptor.add(m_currentBook);
		} catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	
}
