package w3se.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTable;

import w3se.Base.Book;
import w3se.Base.LogItem;
import w3se.Base.LogItemFactory;
import w3se.Base.User;
import w3se.Model.IMS;
import w3se.Model.Task;
import w3se.Model.Database.BookDB;
import w3se.Model.Database.DatabaseAdaptor;
import w3se.View.Panels.SellManagePanel;

public class SellViewController extends AbstractController
{
	private IMS m_model = null;
	private SellManagePanel m_view = null;
	
	public SellViewController(IMS model)
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
						
						m_model.getTaskManager().addTask(new Task(User.WORKER, new 
								Runnable()
								{
									public void run()
									{	
										String[] term = new String[2];
										term[0] = BookDB.KEYWORD;
										term[1] = search;
										m_model.findBook(term);						// find the book
										m_model.addToSoldList(m_model.getCurrentBook());// then add it to the sold list
									}
								}));
					}
				});
		
		addListener("results_clear", new
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
										m_model.setListOfFoundBooks(new ArrayList<Book>());
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
						
						m_model.getTaskManager().addTask(new Task(User.WORKER, new 
								Runnable()
								{
									public void run()
									{
										Book book = m_model.getListOfFoundBooks().get(row);
										m_model.addToSoldList(book);
									}
								}));
					}
				});
		
		addListener("receipt_list", new
				ListenerAdaptor()
				{
					public void mouseClicked(MouseEvent e)
					{
						JTable table = (JTable)e.getSource();
						final int row = table.rowAtPoint(e.getPoint());
						
						m_model.getTaskManager().addTask(new Task(User.WORKER, new 
								Runnable()
								{
									public void run()
									{
										m_model.removeFromSoldList(row);
									}
								}));
					}
				});
		
		addListener("receipt_cancel", new
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_model.getTaskManager().addTask(new Task(User.WORKER, new 
								Runnable()
								{
									public void run()
									{
										m_model.removeAllFromSoldList();
									}
								}));
					}
					
				});
		
				
		addListener("receipt_sell", new
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_model.getTaskManager().addTask(new Task(User.WORKER, new 
								Runnable()
								{
									public void run()
									{
										ArrayList<Book> books = m_model.getListofSoldBooks();
										
										for (int i = 0; i < books.size(); i++)
										{
											m_model.addLog(LogItemFactory.createLogItem(new Date().toString(), LogItem.SALES, books.get(i).getTitle()+" sold for "+books.get(i).getPrice()+"."));
										}
										m_model.finalizeSell();
									}
								}));
					}
				});
	}

	
	@Override
	public void registerView(Object view)
	{
		m_view = (SellManagePanel)view;
		m_model.addObserver(m_view);
	}

}
