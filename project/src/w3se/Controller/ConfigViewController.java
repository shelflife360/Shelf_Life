package w3se.Controller;

import java.awt.event.ActionEvent;

import w3se.Base.ListenerAdaptor;
import w3se.Base.User;
import w3se.Model.IMS;
import w3se.Model.Task;
import w3se.View.Panels.ConfigManagePanel;

public class ConfigViewController extends AbstractController
{
	private IMS m_model = null;
	private ConfigManagePanel m_view = null;
	
	public ConfigViewController(IMS model)
	{
		m_model = model;
		propagateMap();
	}
	
	protected void propagateMap()
	{
		// add the call back for the login button event
		addListener("add_user", new 
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						
						m_model.getScheduler().addTask(new Task(User.MANAGER, new 
								Runnable()
								{
									public void run()
									{
										User user = new User();
										user.setUID(m_view.getUID());
										user.setPrivilege(m_view.getPrivilege());
										user.setUsername(m_view.getUsername());
										user.setPassword(m_view.getPassword());
										
										m_model.createUser(user);
									}
								}));
					}
				});
		
		// create and add a listener for the logout button event
		addListener("", new
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_model.getScheduler().addTask(new Task(User.WORKER, new 
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
		m_view = (ConfigManagePanel)view;
	}

	

}
