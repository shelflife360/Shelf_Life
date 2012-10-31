package w3se.Controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import w3se.Base.Book;
import w3se.Base.User;
import w3se.Model.IMS;
import w3se.Model.Task;
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
										m_model.findBook(search);
									}
								}));
					}
				});
		
		addListener("results_clear", new
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
										m_model.setListOfBooks(new ArrayList<Book>());
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
									}
								}));
					}
				});
		
		addListener("results_list", new
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_model.getTaskManager().addTask(new Task(User.GENERAL, new 
								Runnable()
								{
									public void run()
									{
										m_model.selectBook(m_view.getSelectedBook());
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
