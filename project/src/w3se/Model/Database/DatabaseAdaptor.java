package w3se.Model.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.Server;

import w3se.Base.Book;
import w3se.Base.User;
import w3se.Model.DelimitedString;

public class DatabaseAdaptor implements Database
{
	public static final int SHELF_LIFE_DB = 0;
	public static final int ONLINE_DB = 1;
	public static final int USERS_DB = 2;
	public static final int LOGS_DB = 3;
	
	private Object m_returnObj = null;
	private ShelfLifeDB m_shelfLifeDB;
	private UsersDB m_usersDB;
	private OnlineDB m_onlineDB;
	private int m_state = -1;
	private DBServer m_server = null;
	
	public DatabaseAdaptor()
	{
		String[] serverProperties = new String[6];
		serverProperties[0] = "file:database/UsersDB";
		serverProperties[1] = "UsersDB";
		serverProperties[2] = "file:database/BooksDB";
		serverProperties[3] = "BooksDB";
		serverProperties[4] = "file:database/LogsDB";
		serverProperties[5] = "LogsDB";
		
		m_server = new DBServer(serverProperties, 9001);
		m_server.start();
		
		m_shelfLifeDB = new ShelfLifeDB();
		m_usersDB = new UsersDB();
		m_onlineDB = new OnlineDB();
	}
	
	public void setState(int state)
	{
		m_state = state;
	}
	
	public void retrieve(Object term) throws Exception
	{
		
		switch (m_state)
		{
			case SHELF_LIFE_DB:
				m_shelfLifeDB.retrieve(term);
				m_returnObj = m_shelfLifeDB.getResult();
				break;
				
			case ONLINE_DB:
				m_onlineDB.retrieve(term);
				m_returnObj = m_onlineDB.getResult();
				break;
				
			case USERS_DB:
				m_usersDB.retrieve(term);
				m_returnObj = m_usersDB.getResult();
				break;
			
			case LOGS_DB:
				break;
		}
		
	}
	
	public void add(Object obj) throws SQLException
	{
		switch (m_state)
		{
			case SHELF_LIFE_DB:
				m_shelfLifeDB.add((Book)obj);
				break;
				
			case ONLINE_DB:
				break;
				
			case USERS_DB:
				m_usersDB.add((User)obj);
				break;
			
			case LOGS_DB:
				break;
		}
	}
	
	
	public Object getResult() throws Exception
	{
		return m_returnObj;
	}
	
	
	public void shutdown() throws SQLException
	{
		m_server.shutdown();
	}


	@Override
	public void close() throws SQLException
	{
		m_usersDB.close();
		m_shelfLifeDB.close();
		shutdown();
	}


	
}
