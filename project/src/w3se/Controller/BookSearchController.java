package w3se.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;

import w3se.Model.IMS;
import w3se.Model.Task;
import w3se.Model.Base.Book;
import w3se.Model.Base.LogItem;
import w3se.Model.Base.LogItemFactory;
import w3se.Model.Base.User;
import w3se.Model.Database.BookDB;
import w3se.View.Panels.BookSearchPanel;

public class BookSearchController extends AbstractController
{
	private IMS m_model = null;
	private BookSearchPanel m_view = null;
	
	public BookSearchController(IMS model)
	{
		m_model = model;
		propagateMap();
	}
	
	protected void propagateMap()
	{
		addListener("search_term", new
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						final String search = m_view.getSearchTerm();
						m_model.getTaskManager().addTask(new Task(User.GENERAL, new 
								Runnable()
								{
									public void run()
									{
										String[] term = new String[2];
										
										if (m_view.getState() == BookSearchPanel.GENERAL_SEARCH)
											term[0] = BookDB.KEYWORD;
										else
											term[0] = BookDB.ONLINE;
										
										term[1] = search;
										m_model.findBook(term);
										ArrayList<Book> books = m_model.getListOfFoundBooks();
										
										if (books.size() > 0)
											m_model.setCurrentBook(books.get(0));
									}
								}));
					}
				});
		
		addListener("results_clear", new
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_model.getTaskManager().addTask(new Task(User.GENERAL, new 
								Runnable()
								{
									public void run()
									{
										m_model.setCurrentBook(new Book());
										m_model.setListOfFoundBooks(new ArrayList<Book>());
									}
								}));
					}
				});
		
		addListener("info_clear", new
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_model.getTaskManager().addTask(new Task(User.WORKER, new 
								Runnable()
								{
									public void run()
									{
										m_model.setCurrentBook(new Book());
									}
								}));
					}
				});
		
		addListener("info_accept", new
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_model.getTaskManager().addTask(new Task(User.WORKER, new 
								Runnable()
								{
									public void run()
									{
										m_model.setCurrentBook(m_view.getBook());
										m_model.addCurrentBookToDB();
										m_model.addLog(LogItemFactory.createLogItem(LogItem.INVENTORY, m_view.getBook().getTitle()+" added to the database."));
										m_model.setCurrentBook(new Book());
										
									}
								}));
					}
				});
		
		addListener("results_list", new
				ListenerAdaptor()
				{
					public void mouseClicked(MouseEvent e)
					{
						JTable table = (JTable)e.getSource();
						final int row = table.rowAtPoint(e.getPoint());
						
						m_model.getTaskManager().addTask(new Task(User.GENERAL, new 
								Runnable()
								{
									public void run()
									{
										Book book = m_model.selectBook(row);
										m_model.setCurrentBook(book);
									}
								}));
					}
				});
		
		addListener("tab_changed", new
				ListenerAdaptor()
				{
					public void stateChanged(ChangeEvent e)
					{
						m_model.getTaskManager().addTask(new Task(User.GENERAL, new 
								Runnable()
								{
									public void run()
									{
									}
								}));
					}
				});
		
		addListener("browse_search", new
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_model.getTaskManager().addTask(new Task(User.GENERAL, new 
								Runnable()
								{
									public void run()
									{
										String[] term = new String[3];
										term[0] = BookDB.BROWSE;
										term[1] = m_view.getGenre();
										term[2] = ""+m_view.getOrder();
										m_model.findBook(term);
									}
								}));
					}
				});
		
		addListener("remove_book", new
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_model.getTaskManager().addTask(new Task(User.WORKER, new 
								Runnable()
								{
									public void run()
									{
										Book book = m_view.getBook();
										int result = JOptionPane.showConfirmDialog(null, "Remove book "+book.getTitle()+"?", "Remove Book From Database", JOptionPane.YES_NO_OPTION);
										
										if (result == JOptionPane.YES_OPTION)
											m_model.removeBookFromDB(book);
									}
								}));
					}
				});
		
	}


	@Override
	public void registerView(Object view)
	{
		m_view = (BookSearchPanel)view;
	}


}
