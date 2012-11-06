package w3se.Model.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import w3se.Base.LogItem;
import w3se.Model.Configurations;

public class LogsDB implements Database<LogItem, ArrayList<LogItem>>
{
	private Statement m_statement = null;
	private ResultSet m_resultSet = null;
	private Connection m_connection = null;
	
	public LogsDB(Configurations config)
	{
		try
		{
			Class.forName(config.getValue("DatabaseDriver")).newInstance();
			m_connection = DriverManager.getConnection(config.getValue("LogsDBUrl"));
		} catch (Exception e)
		{
			System.out.println("Failed to open JDBC Driver");
		}
	}

	@Override
	public void retrieve(Object term) throws Exception
	{
		String searchTerm;
		
		if (((LogItem)term).getAction() == LogItem.ALL)
			searchTerm = "";
		else
			searchTerm = ""+((LogItem)term).getAction();
		
		m_statement = m_connection.createStatement();
		m_resultSet = m_statement.executeQuery("SELECT * FROM Logs WHERE Action LIKE "+searchTerm+"");
	}

	@Override
	public ArrayList<LogItem> getResult() throws Exception
	{
		ArrayList<LogItem> items = new ArrayList<LogItem>();
		
		while (m_resultSet.next())
		{
			LogItem log = new LogItem();
		
			log.setID(m_resultSet.getInt("L_id"));
			log.setTimeStamp(m_resultSet.getString("Timestamp"));
			log.setAction(m_resultSet.getInt("Action"));
			log.setDesc(m_resultSet.getString("Description"));
			
			items.add(log);
		}
		return items;	
	}
	
	public void add(LogItem log) throws SQLException
	{
		m_statement = m_connection.createStatement();
		m_statement.execute("INSERT INTO Logs VALUES ("+log.getID()+", '"+log.getTimeStamp()+"', "+log.getAction()+",'"+log.getDesc()+"'); COMMIT");	
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

	public void remove(LogItem log) throws Exception
	{
		m_statement = m_connection.createStatement();
		m_statement.execute("DELETE FROM Logs WHERE L_id = "+log.getID()+";");
	}

	
}

