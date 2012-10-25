package w3se.Model.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import w3se.Base.Book;


public class ShelfLifeDB implements Database
{
	private Statement m_statement = null;
	private ResultSet m_resultSet = null;
	private Connection m_connection = null;
	
	public ShelfLifeDB(Connection connection)
	{
		m_connection = connection;
	}
	
	
	public void retrieve(String searchTerm) throws SQLException
	{
			m_statement = m_connection.createStatement();
			m_resultSet = m_statement.executeQuery("SELECT * FROM Books WHERE Author = " + searchTerm+"%");
	}
	
	public Book getResult() throws SQLException
	{
		Book book = new Book();
		
			while (m_resultSet.next())
			{
				book.setTitle(m_resultSet.getString("Title"));
				book.setISBN(m_resultSet.getString("ISBN"));
				book.setPrice(m_resultSet.getString("Price"));
				book.setAuthor(m_resultSet.getString("Author"));
				book.setPublisher(m_resultSet.getString("Publisher"));
				book.setGenres(m_resultSet.getString("Genres"));
				book.setDescription(m_resultSet.getString("Description"));
			}
		
		return book;
	}
	
	public void shutdown() throws SQLException
	{
		m_statement = m_connection.createStatement();
		m_statement.execute("SHUTDOWN");
	}
	
	public void close() throws SQLException
	{
		m_statement.close();
		if (m_resultSet != null)
			m_resultSet.close();
	}
	
	@Override
	public boolean add(Object obj) throws SQLException
	{
		// TODO Auto-generated method stub
		return false;
	}
	
}
