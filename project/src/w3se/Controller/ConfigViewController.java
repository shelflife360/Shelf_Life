package w3se.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

import w3se.Base.ExcelExporter;
import w3se.Base.Exportable;
import w3se.Base.LogItem;
import w3se.Base.LogItemFactory;
import w3se.Base.User;
import w3se.Model.Configurations;
import w3se.Model.IMS;
import w3se.Model.Task;
import w3se.Model.Database.DatabaseAdaptor;
import w3se.View.Panels.ConfigManagePanel;

public class ConfigViewController extends AbstractController
{
	private IMS m_model = null;
	private ConfigManagePanel m_view = null;
	
	/**
	 * constructor
	 * @param model 
	 */
	public ConfigViewController(IMS model)
	{
		m_model = model;
		propagateMap();
	}
	
	protected void propagateMap()
	{
		// add the call back for the login button event
		addListener("config_add_user", new 
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						
						m_model.getTaskManager().addTask(new Task(User.MANAGER, new 
								Runnable()
								{
									public void run()
									{
										m_model.createUser(m_view.getUser());
										m_model.setVolatileUser(new User());
										m_model.addLog(LogItemFactory.createLogItem(new Date().toString(), LogItem.USER, m_view.getUser().getUsername()+" added to the database."));
									}
								}));
					}
				});
		
		addListener("config_user_search", new
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_model.getTaskManager().addTask(new Task(User.MANAGER, new 
								Runnable()
								{
									public void run()
									{
										User user = m_model.retreiveUser(new String[]{m_view.getSearchBy(),m_view.getSearchTerm()}).get(0);
										m_model.setVolatileUser(user);
									}
								}));
					}
				});
		
		addListener("config_remove_user_search", new
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_model.getTaskManager().addTask(new Task(User.MANAGER, new 
								Runnable()
								{
									public void run()
									{
										
										User user = m_model.retreiveUser(new String[]{m_view.getSearchBy(),m_view.getSearchTerm()}).get(0);
										m_model.addToRemoveUserList(user);
									}
								}));
					}
				});
		
		addListener("config_remove_user_remove", new
				ListenerAdaptor()
				{
					public void mouseClicked(MouseEvent e)
					{
						JTable table = (JTable)e.getSource();
						final int row = table.rowAtPoint(e.getPoint());
						
						m_model.getTaskManager().addTask(new Task(User.MANAGER, new 
							Runnable()
							{
								public void run()
								{
									m_model.removeFromRemoveUserList(row);
								}
							}));
					}
				});
		
		addListener("config_remove_user_clear", new
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_model.getTaskManager().addTask(new Task(User.MANAGER, new 
							Runnable()
							{
								public void run()
								{
									m_model.clearRemoveUserList();
								}
							}));
					}
				});
		
		addListener("config_remove_user_accept", new
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_model.getTaskManager().addTask(new Task(User.MANAGER, new 
							Runnable()
							{
								public void run()
								{
									for (int i = 0; i < m_model.getRemoveUserList().size(); i++)
									{
										m_model.addLog(LogItemFactory.createLogItem(new Date().toString(), LogItem.USER, m_model.getRemoveUserList().get(i).getUsername()+" removed from the database."));
										m_model.removeUserFromDB(m_model.getRemoveUserList().get(i));
									}
									m_model.clearRemoveUserList();
								}
							}));
					}
				});
		
		addListener("config_sql_accept", new
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_model.getTaskManager().addTask(new Task(User.MANAGER, new 
							Runnable()
							{
								public void run()
								{
									String[] params = m_view.getSQLParams();
									Configurations config = IMS.getInstance().getInstance().getConfigurations();
									config.setNewConfiguration("DatabaseDriver", params[0]);
									config.setNewConfiguration("BooksDBUrl", params[1]);
									config.setNewConfiguration("UsersDBUrl", params[2]);
									config.setNewConfiguration("LogsDBUrl", params[3]);
									config.setNewConfiguration("ServerMode", params[4]);
								}
							}));
					}
				});
		
		addListener("books_export", new
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_model.getTaskManager().addTask(new Task(User.WORKER, new 
							Runnable()
							{
								public void run()
								{
									JFileChooser dialog = new JFileChooser();
									FileNameExtensionFilter filter = new FileNameExtensionFilter("EXCEL Files", "xls");
									dialog.setFileFilter(filter);
									int returnVal = dialog.showSaveDialog(null);
									
									m_model.setExporter(new ExcelExporter());
								
									if (returnVal == JFileChooser.APPROVE_OPTION)
									{
										m_model.export(DatabaseAdaptor.BOOK_DB, dialog.getSelectedFile().getAbsolutePath()+".xls");
									}
								}
							}));
					}
				});
		
		addListener("users_export", new
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_model.getTaskManager().addTask(new Task(User.MANAGER, new 
							Runnable()
							{
								public void run()
								{
									JFileChooser dialog = new JFileChooser();
									FileNameExtensionFilter filter = new FileNameExtensionFilter("EXCEL Files", "xls");
									dialog.setFileFilter(filter);
									int returnVal = dialog.showSaveDialog(null);
									
									m_model.setExporter(new ExcelExporter());
								
									if (returnVal == JFileChooser.APPROVE_OPTION)
									{
										m_model.export(DatabaseAdaptor.USERS_DB, dialog.getSelectedFile().getAbsolutePath()+".xls");
									}
								}
							}));
					}
				});
		
		addListener("logs_export", new
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_model.getTaskManager().addTask(new Task(User.MANAGER, new 
							Runnable()
							{
								public void run()
								{
									JFileChooser dialog = new JFileChooser();
									FileNameExtensionFilter filter = new FileNameExtensionFilter("EXCEL Files", "xls");
									dialog.setFileFilter(filter);
									int returnVal = dialog.showSaveDialog(null);
									
									m_model.setExporter(new ExcelExporter());
								
									if (returnVal == JFileChooser.APPROVE_OPTION)
									{
										m_model.export(DatabaseAdaptor.LOGS_DB, dialog.getSelectedFile().getAbsolutePath()+".xls");
									}
								}
							}));
					}
				});
	}

	@Override
	public void registerView(Object view)
	{
		m_view = (ConfigManagePanel)view;
	}

	

}
