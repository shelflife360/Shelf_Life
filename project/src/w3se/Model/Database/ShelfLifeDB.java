package w3se.Model.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.Server;

import w3se.Base.Book;
import w3se.Model.DelimitedString;


public class ShelfLifeDB implements Database<Book, ArrayList<Book>>
{
	private Statement m_statement = null;
	private ResultSet m_resultSet = null;
	private Connection m_connection = null;
	
	public static final int BOOK_LIST_SIZE = 100;
	
	public ShelfLifeDB()
	{	
		try
		{
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			m_connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/BooksDB", "SA", "");
		} catch (Exception e)
		{
			System.out.println("Failed to open JDBC Driver");
		}
	}
	
	
	public void retrieve(Object term ) throws SQLException
	{		
		String[] searchTerm = (String[])term;
		
		if (searchTerm[0].equalsIgnoreCase("KEYWORD"))
		{
			m_statement = m_connection.createStatement();
			m_resultSet = m_statement.executeQuery("SELECT * FROM Books WHERE ISBN LIKE '%"+searchTerm[1]+
				"%' OR Title LIKE '%"+searchTerm[1]+"%' OR Author LIKE '%"+searchTerm[1]+"%'"+
				"OR Publisher LIKE '%"+searchTerm[1]+"%'");
		}
		else if (searchTerm[0].equalsIgnoreCase("BROWSE"))
		{
			m_statement = m_connection.createStatement();
			m_resultSet = m_statement.executeQuery("SELECT * FROM Books WHERE GENRES LIKE '%"+searchTerm[1]+
				"%'");
		}
	}
	
	public ArrayList<Book> getResult() throws SQLException
	{
		ArrayList<Book> bookList = new ArrayList<Book>();
		
			while (m_resultSet.next() && bookList.size() < BOOK_LIST_SIZE)
			{
				Book book = new Book();
				book.setTitle(m_resultSet.getString("Title"));
				book.setISBN(m_resultSet.getString("ISBN"));
				book.setPrice(m_resultSet.getDouble("Price"));
				book.setAuthor(m_resultSet.getString("Author"));
				book.setPublisher(m_resultSet.getString("Publisher"));
				book.setGenres(m_resultSet.getString("Genres"));
				book.setDescription(m_resultSet.getString("Description"));
				book.setQuantity(m_resultSet.getInt("Quantity"));
				book.setOldQuantity(m_resultSet.getInt("Quantity"));
				bookList.add(book);
			}
			
		return bookList;
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
		catch(Exception e)
		{
			System.out.println(e.getMessage());
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
		book.setOldQuantity(obj.getOldQuantity());
		book.setGenres(obj.getGenres().replace("'", "''"));
		
		return book;
	}
	
	@Override
	public void add(Book obj) throws SQLException
	{
		retrieve((obj.getISBN()));
		//ArrayList<Book> list = getResult();
		Book book = adjustStatement(obj);
		
		// if no book is already there
		if (!m_resultSet.next())
		{
			//*********** come back to this for concurrency issues ***********
			/*Book oldBook = list.get(0);
			if (book.getOldQuantity() != oldBook.getQuantity())
			{
				if (book.getOldQuantity() > oldBook.getQuantity())
				{
					
				}
			}*/
			m_statement = m_connection.createStatement();
			m_statement.execute("INSERT INTO Books VALUES ('"+book.getISBN()+"', '"+book.getTitle()+"','"+book.getAuthor()+"','"+book.getPublisher()+"',"+book.getPrice()+",'"+book.getGenres()+"','"+book.getDescription()+"',"+book.getQuantity()+"); COMMIT");
		}
		// if no book is already there
		else
		{
			int quantity = obj.getQuantity();
			m_statement = m_connection.createStatement();
			m_statement.execute("UPDATE Books "+
					"SET ISBN='"+book.getISBN()+"', Title='"+book.getTitle()+"', Author='"+book.getAuthor()+"', Publisher='"
					+book.getPublisher()+"', Price="+book.getPrice()+", Genres='"+book.getGenres()+"', Description='"+book.getDescription()+"', Quantity="+quantity+" " +
					"WHERE ISBN='"+book.getISBN()+"'; COMMIT");
		}
	}
	
}
