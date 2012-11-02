package w3se.Model.Database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.Server;

import w3se.Base.User;
import w3se.Model.DelimitedString;

public class UsersDB implements Database<User, User>
{
	private Statement m_statement = null;
	private ResultSet m_resultSet = null;
	private Connection m_connection = null;
	
	public UsersDB()
	{
		try
		{
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			m_connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/UsersDB", "SA", "");
		} catch (Exception e)
		{
			System.out.println("Failed to open JDBC Driver");
		}
	}

	@Override
	public void retrieve(Object term) throws Exception
	{
		String searchTerm = (String)term;
		m_statement = m_connection.createStatement();
		m_resultSet = m_statement.executeQuery("SELECT * FROM Users WHERE Username = '"+searchTerm+"'");
	}

	@Override
	public User getResult() throws Exception
	{
		User user = new User();
		
		m_resultSet.next();
		user.setUID(m_resultSet.getInt("U_id"));
		user.setPrivilege(m_resultSet.getInt("Privilege"));
		user.setUsername(m_resultSet.getString("Username"));
		user.setHashedPassword(m_resultSet.getString("Password"));
		
		return user;	
	}
	
	public void add(User user) throws SQLException
	{
		m_statement = m_connection.createStatement();
		m_statement.execute("INSERT INTO Users VALUES ("+user.getUID()+", "+user.getPrivilege()+", '"+user.getUsername()+"','"+user.getPassword()+"'); COMMIT");	
	}
	
	public void shutdown() throws SQLException
	{
		m_statement = m_connection.createStatement();
		m_statement.execute("SHUTDOWN");
		m_statement.close();
	}
	
	public void close() throws SQLException
	{
		try
		{
			shutdown();
			m_connection.close();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
	}
}
