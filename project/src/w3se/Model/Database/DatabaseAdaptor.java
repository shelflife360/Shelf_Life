package w3se.Model.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import w3se.Base.User;

public class DatabaseAdaptor implements Database
{
	private Connection m_connection = null;
	public static final int SHELF_LIFE_DB = 0;
	private Object m_returnObj = null;
	private ShelfLifeDB m_shelfLifeDB;
	private UsersDB m_usersDB;
	
	public DatabaseAdaptor()
	{
			try
			{
				Class.forName("org.hsqldb.jdbc.JDBCDriver");
			
			m_connection = DriverManager.getConnection("jdbc:hsqldb:file:database/SLDB", "SA", "");
			m_shelfLifeDB = new ShelfLifeDB(m_connection);
			m_usersDB = new UsersDB(m_connection);
			} 
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}catch (SQLException e)
			{
				System.out.println(e.getMessage());
			}
	}
	
	public void retrieve(String term) throws Exception
	{
		m_usersDB.retrieve(term);
		m_returnObj = m_usersDB.getResult();
	}
	
	public boolean add(Object obj) throws SQLException
	{
		return m_usersDB.add((User)obj);
	}
	
	
	public Object getResult() throws Exception
	{
		return m_returnObj;
	}
	
	
	public void shutdown() throws SQLException
	{
		//m_shelfLifeDB.shutdown();
		//m_shelfLifeDB.close();
		
		m_usersDB.shutdown();
		m_usersDB.close();
		m_connection.close();
	}


	@Override
	public void close() throws SQLException
	{
		
	}


	
}
