package w3se.Model.Base;

import w3se.Model.Exportable;

public class Book implements Exportable
{
	private int m_id = 0;
	private String m_title = "";
	private String m_ISBN = "";
	private double m_price = 0.0f;
	private int m_oldQuantity = 0;
	private String m_author = "";
	private String m_publisher = "";
	private String m_genres = "";
	private String m_description = "";
	private int m_quantity = 0;
	private int m_totalCopies = 0;
	
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
	
	public int getOldQuantity()
	{
		return m_oldQuantity;
	}
	
	public void setOldQuantity(int quantity)
	{
		m_oldQuantity = quantity;
	}
	
	public int getQuantity()
	{
		return m_quantity;
	}
	
	public void setQuantity(int quantity)
	{
		m_quantity = quantity;
	}
	
	public int getTotalCopies()
	{
		return m_totalCopies;
	}
	
	public void setTotalCopies(int total)
	{
		m_totalCopies = total;
	}

	
	public String getExportableTitle()
	{
		return "Book Database Export";
	}
	
	public String[] getExportableHeadRow()
	{
		String[] format = new String[8];
		format[0] = "ISBN";
		format[1] = "Title";
		format[2] = "Author";
		format[3] = "Publisher";
		format[4] = "Genres";
		format[5] = "Description";
		format[6] = "Quantity";
		format[7] = "Price";
		
		return format;
	}
	
	public String[] getExportableRow()
	{
		String[] format = new String[8];
		format[0] = m_ISBN;
		format[1] = m_title;
		format[2] = m_author;
		format[3] = m_publisher;
		format[4] = m_genres;
		format[5] = m_description;
		format[6] = ""+m_quantity;
		format[7] = ""+m_price;
		
		return format;
	}
}
