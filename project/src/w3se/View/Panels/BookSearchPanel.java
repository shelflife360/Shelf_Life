package w3se.View.Panels;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import w3se.Base.Book;
import w3se.Controller.Controller;
import w3se.Model.IMS;
import w3se.View.Subpanels.BookInfoPanel;
import w3se.View.Subpanels.SearchPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

public class BookSearchPanel extends JPanel implements Observer
{
	public static final int WIDTH = 1020;
	public static final int HEIGHT = 500;
	/*public static final String SEARCH_FIELD = "search_field";
	public static final String RESULT_LIST = "result_list";
	public static final String PREV_VIEWED_LIST = "prev_viewed_list";
	public static final String GENRES = "genres";
	public static final String DISPLAY_ORDER = "display_order";*/
	
	private SearchPanel m_mainPanel;
	private BookInfoPanel m_infoPanel;
	
	/**
	 * Create the panel.
	 */
	public BookSearchPanel(int headerType, boolean editable, Controller controller)
	{
		controller.registerView(this);
		IMS.getInstance().addObserver(this);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(new BorderLayout());
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setEnabled(true);
		add(splitPane, BorderLayout.CENTER);
		
		m_mainPanel = new SearchPanel(headerType, controller);
		m_infoPanel = new BookInfoPanel(editable, controller);
		
		splitPane.setLeftComponent(m_mainPanel);
		splitPane.setRightComponent(m_infoPanel);

	}
	
	/**
	 * method to retrieve the search term from the form
	 * @param searchTerm
	 */
	public void setSearchTerm(String searchTerm)
	{
		
	}
	
	/**
	 * method to retrieve the search term from the form
	 * @param searchTerm
	 * return 
	 */
	public String getSearchTerm()
	{
		return m_mainPanel.getSearchText();
	}

	public Book getBook()
	{
		Book book = new Book();
		
		book.setTitle(m_infoPanel.getTitle());
		book.setISBN(m_infoPanel.getISBN());
		book.setPrice(Double.parseDouble(m_infoPanel.getPrice()));
		book.setAuthor(m_infoPanel.getAuthor());
		book.setPublisher(m_infoPanel.getPublisher());
		book.setGenres(m_infoPanel.getGenreList());
		book.setDescription(m_infoPanel.getDesc());
		
		return book;
	}
	
	public void update(Observable sender, Object obj)
	{
		Book book = IMS.getInstance().getCurrentBook();
		m_mainPanel.clearSearchText();
		m_infoPanel.setTitle(book.getTitle());
		m_infoPanel.setISBN(book.getISBN());
		m_infoPanel.setPrice(""+book.getPrice());
		m_infoPanel.setAuthor(book.getAuthor());
		m_infoPanel.setPublisher(book.getPublisher());
		m_infoPanel.setDesc(book.getDescription());
		m_infoPanel.setGenreList(book.getGenres());
		m_mainPanel.clearLists();
		m_mainPanel.setSearchResults(IMS.getInstance().getListOfBooks());
	}

	public int getSelectedBook()
	{
		return m_mainPanel.getSelectedBookIndex();
	}
	
	//public void get
}
