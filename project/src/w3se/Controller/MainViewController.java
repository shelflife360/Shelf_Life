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
		addListener("exit", new 
				ListenerAdaptor()
				{
					public void windowClosing(WindowEvent e)
					{
						m_model.shutdownSystem();
					}
				});
		
		addListener("tab_change", new
				ListenerAdaptor()
				{
					public void stateChanged(ChangeEvent e)
					{
						final JTabbedPane tabs = (JTabbedPane)e.getSource();
						
						/*if (tabs.getSelectedIndex() == MainView.MANAGE_TAB)
						{
							m_model.getTaskManager().addTask(new Task(User.GENERAL, new 
									Runnable()
									{
										public void run()
										{
											tabs.setSelectedIndex(MainView.LOGIN_TAB);
											System.out.println("General");
										}
									}));
							
							m_model.getTaskManager().addTask(new Task(User.WORKER, new 
								Runnable()
								{
									public void run()
									{
										tabs.setSelectedIndex(MainView.MANAGE_TAB);
										System.out.println("Worker");
									}
								}));
							
							
						}*/
					}
				});
	}
	
	@Override
	public void registerView(Object view)
	{
		m_view = (MainView)view;
	}

}
