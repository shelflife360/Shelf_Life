package w3se.Controller;

import java.awt.event.ActionEvent;
import java.util.Date;

import javax.swing.JOptionPane;

import w3se.Base.LogItem;
import w3se.Base.LogItemFactory;
import w3se.Base.User;
import w3se.Model.IMS;
import w3se.Model.Task;
import w3se.View.Panels.LoginPanel;

public class LoginViewController extends AbstractController
{
	private IMS m_model = null;
	private LoginPanel m_view = null;
	
	public LoginViewController(IMS model)
	{
		m_model = model;
		propagateMap();
	}
	
	protected void propagateMap()
	{
		// add the call back for the login button event
		addListener("login", new 
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						
						m_model.getTaskManager().addTask(new Task(User.GENERAL, new 
								Runnable()
								{
									public void run()
									{
										m_model.setUser(new User(-1, User.GENERAL, m_view.getUsername(), m_view.getPassword()));
										try
										{
											m_model.addLog(LogItemFactory.createLogItem(new Date().toString(), LogItem.LOGIN, m_view.getUsername()+" logged onto the system."));
											m_model.login();
										} catch (Exception e)
										{
											if (m_model.showDialogs())
												JOptionPane.showMessageDialog(m_view, e.getMessage(), "Login Failure", JOptionPane.ERROR_MESSAGE);
										}
									}
								}));
					}
				});
		
		// create and add a listener for the logout button event
		addListener("logout", new
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_model.getTaskManager().addTask(new Task(User.GENERAL, new 
								Runnable()
								{
									public void run()
									{
										m_model.addLog(LogItemFactory.createLogItem(new Date().toString(), LogItem.LOGIN, m_model.getCurrentUser().getUsername()+" logged out of the system."));
										m_model.setUser( new User(-1, User.GENERAL, "General", "General"));
										m_model.logout();
									}
								}));
					}
				});
	}

	@Override
	public void registerView(Object view)
	{
		m_view = (LoginPanel)view;
	}

	

}
