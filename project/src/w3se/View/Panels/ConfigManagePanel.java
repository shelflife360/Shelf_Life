package w3se.View.Panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import w3se.Base.User;
import w3se.Controller.Controller;
import w3se.Model.IMS;
import w3se.View.MainView;
import w3se.View.Subpanels.AddUserPanel;
import w3se.View.Subpanels.ConfigSQLPanel;
import w3se.View.Subpanels.ConfigSubPanel;
import w3se.View.Subpanels.RemoveUserPanel;

@SuppressWarnings("serial")
public class ConfigManagePanel extends JPanel implements Observer
{
	public static final int WIDTH = 1020;
	public static final int HEIGHT = 500;
	public static final int NULL = 0;
	public static final int USER_ADD = 1;
	public static final int USER_EDIT = 2;
	public static final int USER_REMOVE = 3;
	public static final int SQL_CONFIG = 4;
	private MainView m_parent = null;
	private Controller m_controller = null;
	private JSplitPane m_splitPane;
	private AddUserPanel m_addUser;
	private RemoveUserPanel m_removeUser;
	private ConfigSQLPanel m_sqlConfig;
	
	private int m_currentPanel = NULL;
	/**
	 * Create the panel.
	 */
	public ConfigManagePanel(MainView mainView, Controller controller)
	{
		m_parent = mainView;
		m_controller = controller;
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(new BorderLayout());
		m_splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		m_splitPane.setEnabled(true);
		add(m_splitPane, BorderLayout.CENTER);
			
		ConfigSubPanel mainPanel = new ConfigSubPanel(this, m_controller);
			
		m_splitPane.setLeftComponent(mainPanel);
		m_splitPane.setRightComponent(new JPanel());
		m_controller.registerView(this);
		IMS.getInstance().addView(this);
		m_addUser = new AddUserPanel(m_controller);
		m_removeUser = new RemoveUserPanel(m_controller);
		m_sqlConfig = new ConfigSQLPanel(m_controller);
		
	}

	public void setInfoPanel(int panel)
	{
		switch (panel)
		{
			case USER_ADD:
				m_addUser.setAddMode();
				m_currentPanel = USER_ADD;
				m_splitPane.setRightComponent(m_addUser);
				break;
			case USER_EDIT:
				m_addUser.setEditMode();
				m_currentPanel = USER_EDIT;
				m_splitPane.setRightComponent(m_addUser);
				break;
			case USER_REMOVE:
				m_currentPanel = USER_REMOVE;
				m_splitPane.setRightComponent(m_removeUser);
				break;
			case SQL_CONFIG:
				m_currentPanel = SQL_CONFIG;
				m_splitPane.setRightComponent(m_sqlConfig);
				break;
			default:
				m_currentPanel = NULL;
				m_splitPane.setRightComponent(new JPanel());
				break;
		}
		
	}
	
	public String getSearchTerm()
	{
		if (m_currentPanel == USER_EDIT)
			return m_addUser.getSearchTerm();
		else if (m_currentPanel == USER_REMOVE)
			return m_removeUser.getSearchTerm();
		else
			return "";
	}
	
	public String getSearchBy()
	{
		if (m_currentPanel == USER_EDIT)
			return m_addUser.getSearchBy();
		else if (m_currentPanel == USER_REMOVE)
			return m_removeUser.getSearchBy();
		else
			return "";
	}
	
	public void setUser(User user)
	{
		m_addUser.setUser(user);
	}
	
	public User getUser()
	{
		return m_addUser.getUser();
	}

	public void clear()
	{
		m_addUser.clear();
		m_removeUser.clearList();
	}
	
	public String[] getSQLParams()
	{
		return m_sqlConfig.getSQLParams();
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		clear();
		m_addUser.setUser(IMS.getInstance().getVolatileUser());
		m_removeUser.setRemoveList(IMS.getInstance().getRemoveUserList());
		m_sqlConfig.setSQLParams(IMS.getInstance().getDatabaseAdaptor().getDatabaseConfig());
	}
}
