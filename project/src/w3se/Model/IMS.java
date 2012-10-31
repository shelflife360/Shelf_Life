package w3se.Model;


import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

import w3se.Base.User;
import w3se.Model.Database.DatabaseAdaptor;
import w3se.View.View;

public class IMS extends Observable implements Observer
{
	private States m_state = States.LOGGED_OUT;
	private User m_user = new User(-1, User.GENERAL, "General", "General");
	private static View m_view = null;
	private TaskManager m_scheduler = new TaskManager();
	private static IMS m_instance = null;
	private DatabaseAdaptor m_dbAdaptor = new DatabaseAdaptor();
	
	private IMS()
	{
		m_scheduler.addObserver(this);
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
	 * 
	 * @param user
	 */
	public void setUser(User user)
	{
		m_user = user;
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	public void login() throws Exception
	{
		m_dbAdaptor.retrieve(m_user.getUsername());
		User user = (User)m_dbAdaptor.getResult();
		if (m_user.getUsername().equals(user.getUsername()) && m_user.getPassword().equals(user.getPassword()))
		{
			m_state = States.LOGGED_IN;
			m_user = user;
		}
		else
		{
			m_state = States.LOGGED_OUT;
			throw new Exception("username or password were incorrect");
		}
	}
	
	/**
	 * 
	 */
	public void logout()
	{
		m_state = States.LOGGED_OUT;
	}
	
	public boolean createUser(User user)
	{
		System.out.println(user.getPrivilege());
		try
		{
			return m_dbAdaptor.add(user);
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
			m_dbAdaptor.shutdown();
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
	 * 
	 * @return
	 */
	public User getUser()
	{
		User copy = new User(-1, m_user.getPrivilege(), "DeadBeef", "DeadBeef");
		return copy;
	}
	
	/**
	 * 
	 * @param obs
	 */
	public void addView(Observer obs)
	{
		addObserver(obs);
	}
	
	/**
	 * 
	 * @return
	 */
	public TaskManager getScheduler()
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
			notifyObservers();
		}
	}
	
	
	
}
