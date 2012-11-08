package w3se.Controller;

import java.awt.event.WindowEvent;
import java.util.Date;
import javax.swing.event.ChangeEvent;
import w3se.Model.IMS;
import w3se.Model.Task;
import w3se.Model.Base.LogItem;
import w3se.Model.Base.LogItemFactory;
import w3se.Model.Base.User;
import w3se.View.MainView;

public class MainViewController extends AbstractController
{
	private IMS m_model = null;
	private MainView m_view = null;
	
	public MainViewController(IMS model)
	{
		m_model = model;
		propagateMap();
	}
	
	protected void propagateMap()
	{
		addListener("exit_system", new 
				ListenerAdaptor()
				{
					// cannot run in task manager as it is a shutdown method and will jam the manager
					public void windowClosing(WindowEvent e)
					{
						m_model.addLog(LogItemFactory.createLogItem(LogItem.SYSTEM, "System shutdown."));
						try
						{
							m_model.shutdownSystem();
						} 
						catch (Exception ex){}
					}
					
					public void windowOpened(WindowEvent e)
					{
						m_model.getTaskManager().addTask(new Task(User.GENERAL, new 
								Runnable()
								{
									public void run()
									{
										m_model.addLog(LogItemFactory.createLogItem(LogItem.SYSTEM, "System startup."));
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
	}
	
	@Override
	public void registerView(Object view)
	{
		m_view = (MainView)view;
	}

}
