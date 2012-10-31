package w3se.Base;

public class Book
{
	private int m_id = 0;
	private String m_title = "";
	private String m_ISBN = "";
	private double m_price = 0.0f;
	private String m_author = "";
	private String m_publisher = "";
	private String m_genres = "";
	private String m_description = "";
	private int m_quantity = 0;
	
	public Book()
	{
		
	}
	
	public Book(String title, String ISBN, double price, String author, String publisher, String genres, String description)
	{
		m_title = title;
		m_ISBN = ISBN;
		m_price = price;
		m_author = author;
		m_publisher = publisher;
		m_genres = genres;
		m_description = description;
	}
	
	public int getBookID()
	{
		return m_id;
	}
	
	public void setBookID(int id)
	{
		m_id = id;
	}
	
	public String getTitle()
	{
		return m_title;
	}

	public void setTitle(String title)
	{
		m_title = title;
	}

	public String getISBN()
	{
		return m_ISBN;
	}

	public void setISBN(String ISBN)
	{
		m_ISBN = ISBN;
	}

	public double getPrice()
	{
		return m_price;
	}

	public void setPrice(double price)
	{
		m_price = price;
	}

	public String getAuthor()
	{
		return m_author;
	}

	public void setAuthor(String author)
	{
		m_author = author;
	}

	public String getPublisher()
	{
		return m_publisher;
	}

	public void setPublisher(String publisher)
	{
		m_publisher = publisher;
	}

	public String getGenres()
	{
		return m_genres;
	}

	public void setGenres(String genres)
	{
		m_genres = genres;
	}

	public String getDescription()
	{
		return m_description;
	}

	public void setDescription(String description)
	{
		m_description = description;
	}
	
	public int getQuantity()
	{
		return m_quantity;
	}
	
	public void setQuantity(int quantity)
	{
		m_quantity = quantity;
	}
}
