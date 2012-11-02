package w3se.Controller;

import java.awt.event.WindowEvent;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;

import w3se.Base.User;
import w3se.Model.IMS;
import w3se.Model.Task;
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
					public void windowClosing(WindowEvent e)
					{
						m_model.shutdownSystem();
					}
					
					public void windowOpened(WindowEvent e)
					{
						m_model.getTaskManager().addTask(new Task(User.GENERAL, new 
								Runnable()
								{
									public void run()
									{
										// blank task to force the system to update all of it's views for the first time
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
