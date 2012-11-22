package w3se.Controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

import w3se.Model.Configurations;
import w3se.Model.ExcelExporter;
import w3se.Model.Exportable;
import w3se.Model.IMS;
import w3se.Model.PlainTextExporter;
import w3se.Model.Task;
import w3se.Model.Base.LogItem;
import w3se.Model.Base.LogItemFactory;
import w3se.Model.Base.Theme;
import w3se.Model.Base.User;
import w3se.Model.Database.DatabaseAdaptor;
import w3se.View.Panels.ConfigManagePanel;
import w3se.View.Subpanels.ExportPanel;

public class ConfigViewController extends AbstractController
{
	private IMS m_model = null;
	private ConfigManagePanel m_view = null;
	private boolean m_runOnce = false;
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
										m_model.addLog(LogItemFactory.createLogItem(LogItem.USER, m_view.getUser().getUsername()+" added to the database."));
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
										ArrayList<User> users = m_model.retreiveUser(new String[]{m_view.getSearchBy(),m_view.getSearchTerm()});
										for (int i = 0; i < users.size(); i++)
											m_model.addToRemoveUserList(users.get(i));
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
										int result = JOptionPane.showConfirmDialog(null, "Remove users from the system?", "Remove Users From Database", JOptionPane.YES_NO_OPTION);
										
										if (result == JOptionPane.YES_OPTION)
										{
											m_model.addLog(LogItemFactory.createLogItem(LogItem.USER, m_model.getRemoveUserList().get(i).getUsername()+" removed from the database."));
											User user = m_model.getRemoveUserList().get(i);
											m_model.addLog(LogItemFactory.createLogItem(LogItem.USER, user.getUsername()+" removed from the system."));
											m_model.removeUserFromDB(user);
										}
										
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
									
									if (m_view.getExportType() == ExportPanel.EXCEL_EXP)
										m_model.setExporter(new ExcelExporter());
									else
										m_model.setExporter(new PlainTextExporter());
								
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
									
									if (m_view.getExportType() == ExportPanel.EXCEL_EXP)
										m_model.setExporter(new ExcelExporter());
									else
										m_model.setExporter(new PlainTextExporter());
								
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
									FileNameExtensionFilter filter = new FileNameExtensionFilter("Supported Files", "xls");
									dialog.setFileFilter(filter);
									int returnVal = dialog.showSaveDialog(null);
									
									if (m_view.getExportType() == ExportPanel.EXCEL_EXP)
										m_model.setExporter(new ExcelExporter());
									else
										m_model.setExporter(new PlainTextExporter());
								
									if (returnVal == JFileChooser.APPROVE_OPTION)
									{
										m_model.export(DatabaseAdaptor.LOGS_DB, dialog.getSelectedFile().getAbsolutePath());
									}
								}
							}));
					}
				});
		
		addListener("dialog_toggle", new
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_model.getTaskManager().addTask(new Task(User.WORKER, new 
							Runnable()
							{
								public void run()
								{
									m_model.toggleDialog(m_view.isDialogCheckSelected());
									m_model.getConfigurations().setNewConfiguration("ErrorDialog", ""+m_view.isDialogCheckSelected());
								}
								
							}));
					}
				});
		
		addListener("notify_smode_toggle", new
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_model.getTaskManager().addTask(new Task(User.WORKER, new 
							Runnable()
							{
								public void run()
								{
									m_model.toggleNotifySMode(m_view.isNotifyCheckSelected());
									m_model.getConfigurations().setNewConfiguration("NotifyOfSMode", ""+m_view.isNotifyCheckSelected());
								}
								
							}));
					}
				});
		
		addListener("select_main_color", new
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_model.getTaskManager().addTask(new Task(User.WORKER, new 
							Runnable()
							{
								public void run()
								{
									Theme theme = m_model.getTheme();
									Color bgColor = JColorChooser.showDialog(null, "Choose Main Color", null);
									m_model.setTheme(new Theme(bgColor, theme.getSecondaryColor()));
									m_model.getConfigurations().setNewConfiguration("MainColor", ""+bgColor.getRGB());
								}
								
							}));
					}
				});
		
		addListener("select_secondary_color", new
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_model.getTaskManager().addTask(new Task(User.WORKER, new 
							Runnable()
							{
								public void run()
								{
									Theme theme = m_model.getTheme();
									Color color = JColorChooser.showDialog(null, "Choose Secondary Color", null);
									m_model.setTheme(new Theme(theme.getMainColor(), color));
									m_model.getConfigurations().setNewConfiguration("SecColor", ""+color.getRGB());
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
