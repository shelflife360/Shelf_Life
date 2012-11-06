package w3se.Model.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import w3se.Base.User;
import w3se.Model.Configurations;

public class UsersDB implements Database<User, User>
{
	private Statement m_statement = null;
	private ResultSet m_resultSet = null;
	private Connection m_connection = null;
	public static final String U_ID = "U_id";
	public static final String USERNAME = "Username";
	
	public UsersDB(Configurations config)
	{
		try
		{
			Class.forName(config.getValue("DatabaseDriver")).newInstance();
			m_connection = DriverManager.getConnection(config.getValue("UsersDBUrl"));
		} catch (Exception e)
		{
			System.out.println("Failed to open JDBC Driver");
		}
	}

	@Override
	public void retrieve(Object term) throws Exception
	{
		String[] searchTerm = (String[])term;
			
		if (searchTerm[0].equals(U_ID))
			searchTerm[1] = ""+Integer.parseInt(searchTerm[1])+"";
		else
			searchTerm[1] = "'"+searchTerm[1]+"'";
		
		m_statement = m_connection.createStatement();
		m_resultSet = m_statement.executeQuery("SELECT * FROM Users WHERE "+searchTerm[0]+" = "+searchTerm[1]);
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
		try
		{
			retrieve(new String[]{USERNAME, user.getUsername()});
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		if (!m_resultSet.next())
		{
			m_statement = m_connection.createStatement();
			m_statement.execute("INSERT INTO Users VALUES ("+user.getUID()+", "+user.getPrivilege()+", '"+user.getUsername()+"','"+user.getPassword()+"'); COMMIT");	
		}
		else
		{
			m_statement = m_connection.createStatement();
			m_statement.execute("UPDATE Users "+
					"SET Username='"+user.getUsername()+"', Password='"+user.getPassword()+"', Privilege='"+user.getPrivilege()+"', U_id='"+user.getUID()+"'" +
							"WHERE Username = '"+user.getUsername()+"'; COMMIT");
		}
	}
	
	public void shutdown() throws SQLException
	{
		try
		{
			m_statement = m_connection.createStatement();
			m_statement.execute("SHUTDOWN");
			m_statement.close();
		}
		catch (Exception e)
		{}
	}
	
	public void close() throws SQLException
	{
		try
		{
			m_connection.close();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
	}

	public void remove(User obj) throws Exception
	{
		m_statement = m_connection.createStatement();
		m_statement.execute("DELETE FROM Users WHERE U_id = '"+obj.getUID()+"';");
	}
	
}
