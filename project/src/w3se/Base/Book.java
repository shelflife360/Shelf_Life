package w3se.Base;

public class Book
{
	private String m_title = "";
	private String m_ISBN = "";
	private String m_price = "";
	private String m_author = "";
	private String m_publisher = "";
	private String m_genres = "";
	private String m_description = "";
	
	public Book()
	{
		
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

	public String getPrice()
	{
		return m_price;
	}

	public void setPrice(String price)
	{
		this.m_price = price;
	}

	public String getAuthor()
	{
		return m_author;
	}

	public void setAuthor(String author)
	{
		this.m_author = author;
	}

	public String getPublisher()
	{
		return m_publisher;
	}

	public void setPublisher(String publisher)
	{
		this.m_publisher = publisher;
	}

	public String getGenres()
	{
		return m_genres;
	}

	public void setGenres(String genres)
	{
		this.m_genres = genres;
	}

	public String getDescription()
	{
		return m_description;
	}

	public void setDescription(String description)
	{
		this.m_description = description;
	}

	public Book(String title, String ISBN, String price, String author, String publisher, String genres, String description)
	{
		m_title = title;
		m_ISBN = ISBN;
		m_price = price;
		m_author = author;
		m_publisher = publisher;
		m_genres = genres;
		m_description = description;
	}

}
