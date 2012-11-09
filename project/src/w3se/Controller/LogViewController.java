package w3se.Controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import w3se.Model.IMS;
import w3se.Model.Task;
import w3se.Model.Base.LogItem;
import w3se.Model.Base.LogItemFactory;
import w3se.Model.Base.User;
import w3se.Model.Database.LogsDB;
import w3se.View.Panels.LogManagePanel;
import w3se.View.Panels.LoginPanel;

public class LogViewController extends AbstractController
{
	private IMS m_model = null;
	private LogManagePanel m_view = null;
	
	public LogViewController(IMS model)
	{
		m_model = model;
		propagateMap();
	}
	
	protected void propagateMap()
	{
		
		addListener("search_all", new 
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						
						m_model.getTaskManager().addTask(new Task(User.MANAGER, new 
								Runnable()
								{
									public void run()
									{
										String orderBy;
										
										if (m_view.getOrderByIndex() == 0)
											orderBy = LogsDB.TIME;
										else if (m_view.getOrderByIndex() == 1)
											orderBy = LogsDB.ACTION;
										else
											orderBy = LogsDB.ID;
										m_view.clearLists();
										m_view.setSearchResults(m_model.getLogs(new String[] {LogsDB.ALL, orderBy, ""}));
									}
								}));
					}
				});
		
		addListener("search_by", new 
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						
						m_model.getTaskManager().addTask(new Task(User.MANAGER, new 
								Runnable()
								{
									public void run()
									{
										String orderBy;
										String searchBy;
										String searchTerm;
										
										if (m_view.getOrderByIndex() == 0)
											orderBy = LogsDB.TIME;
										else if (m_view.getOrderByIndex() == 1)
											orderBy = LogsDB.ACTION;
										else
											orderBy = LogsDB.ID;
										
										if (m_view.getSearchByIndex() == 0)
											searchBy = LogsDB.TIME;
										else if (m_view.getSearchByIndex() == 1)
											searchBy = LogsDB.ACTION;
										else
											searchBy = LogsDB.ID;
										
										searchTerm = m_view.getSearchTerm();
										m_view.clearLists();
										m_view.setSearchResults(m_model.getLogs(new String[] {searchBy, orderBy, searchTerm}));
									}
								}));
					}
				});
		
		addListener("clear", new
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_model.getTaskManager().addTask(new Task(User.GENERAL, new 
								Runnable()
								{
									public void run()
									{
										m_model.removeLogs();
										m_view.setSearchResults(new ArrayList<LogItem>());
										m_view.clearLists();
									}
								}));
					}
				});
	}

	@Override
	public void registerView(Object view)
	{
		m_view = (LogManagePanel)view;
	}

}
