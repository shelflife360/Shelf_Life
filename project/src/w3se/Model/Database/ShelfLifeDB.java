package w3se.Model.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import w3se.Base.Book;
import w3se.Model.DelimitedString;


public class ShelfLifeDB implements Database<Book, ArrayList<Book>>
{
	private Statement m_statement = null;
	private ResultSet m_resultSet = null;
	private Connection m_connection = null;
	
	public ShelfLifeDB(Connection connection)
	{
		m_connection = connection;
	}
	
	
	public void retrieve(String searchTerm ) throws SQLException
	{
			StringTokenizer tokenizer = new StringTokenizer(searchTerm, ",");
			String[] strs = new String[2];
			strs[0] = tokenizer.nextToken();
			strs[1] = tokenizer.nextToken();
			
			m_statement = m_connection.createStatement();
			m_resultSet = m_statement.executeQuery("SELECT * FROM Books WHERE "+strs[0]+" LIKE '%"+strs[1]+"%'");
	}
	
	public ArrayList<Book> getResult() throws SQLException
	{
		ArrayList<Book> bookList = new ArrayList<Book>();
		
			while (m_resultSet.next())
			{
				Book book = new Book();
				book.setTitle(m_resultSet.getString("Title"));
				book.setISBN(m_resultSet.getString("ISBN"));
				book.setPrice(Double.parseDouble(m_resultSet.getString("Price")));
				book.setAuthor(m_resultSet.getString("Author"));
				book.setPublisher(m_resultSet.getString("Publisher"));
				book.setGenres(m_resultSet.getString("Genres"));
				book.setDescription(m_resultSet.getString("Description"));
				bookList.add(book);
			}
		
		return bookList;
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
	
	private Book adjustStatement(Book obj)
	{
		Book book = new Book();
		book.setTitle(obj.getTitle().replace("'", "''"));
		book.setAuthor(obj.getAuthor().replace("'", "''"));
		book.setPublisher(obj.getPublisher().replace("'", "''"));
		book.setDescription(obj.getDescription().replace("'", "''"));
		book.setISBN(obj.getISBN());
		book.setBookID(obj.getBookID());
		book.setPrice(obj.getPrice());
		book.setQuantity(obj.getQuantity());
		book.setGenres(obj.getGenres().replace("'", "''"));
		
		return book;
	}
	
	@Override
	public void add(Book obj) throws SQLException
	{
		retrieve(("ISBN,"+obj.getISBN()));
		Book book = adjustStatement(obj);
		// if no book is already there
		if (!m_resultSet.next())
		{
			
			m_statement = m_connection.createStatement();
			m_statement.execute("INSERT INTO Books VALUES (1 , '"+book.getISBN()+"', '"+book.getTitle()+"','"+book.getAuthor()+"','"+book.getPublisher()+"',"+book.getPrice()+",'"+book.getGenres()+"','"+book.getDescription()+"',"+book.getQuantity()+")");
		}
		// if no book is already there
		else
		{
			int quantity = obj.getQuantity();
			m_statement = m_connection.createStatement();
			m_statement.execute("UPDATE Books "+
					"SET B_ID="+1+", ISBN='"+book.getISBN()+"', Title='"+book.getTitle()+"', Author='"+book.getAuthor()+"', Publisher='"
					+book.getPublisher()+"', Price="+book.getPrice()+", Genres='"+book.getGenres()+"', Description='"+book.getDescription()+"', Quantity="+quantity+" " +
					"WHERE ISBN='"+book.getISBN()+"'");
		}
	}
	
}
