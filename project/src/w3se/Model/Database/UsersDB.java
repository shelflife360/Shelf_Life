package w3se.Model.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import w3se.Base.User;
import w3se.Model.DelimitedString;

public class UsersDB implements Database<User, User>
{
	private Statement m_statement = null;
	private ResultSet m_resultSet = null;
	private Connection m_connection = null;
	
	public UsersDB(Connection conn)
	{
		m_connection = conn;
	}

	@Override
	public void retrieve(String searchTerm) throws Exception
	{
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
		m_statement.execute("INSERT INTO Users VALUES ("+user.getUID()+", "+user.getPrivilege()+", '"+user.getUsername()+"','"+user.getPassword()+"')");	
	}
	
	public void shutdown() throws SQLException
	{
	}
	
	public void close() throws SQLException
	{
		if (!m_connection.isClosed())
		{
			m_statement.close();
			m_resultSet.close();
		}
	}
}
