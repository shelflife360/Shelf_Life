package w3se.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;

import w3se.Base.User;
import w3se.Controller.Controller;
import w3se.Controller.ControllerFactory;
import w3se.Model.IMS;
import w3se.Model.States;
import w3se.View.Panels.BrowseSearchPanel;
import w3se.View.Panels.ConfigManagePanel;
import w3se.View.Panels.KeywordSearchPanel;
import w3se.View.Panels.LogManagePanel;
import w3se.View.Panels.LoginPanel;
import w3se.View.Panels.LogoutPanel;
import w3se.View.Panels.SellManagePanel;

public class MainView extends JFrame implements Observer
{

	private JPanel contentPane;
	private Controller m_controller;
	private ControllerFactory m_conFac = ControllerFactory.getInstance();
	
	private static States m_state = States.LOGGED_OUT;
	
	private static final int SEARCH_TAB = 0;
	private static final int LOGIN_TAB = 1;
	private static final int MANAGE_TAB = 2;
	
	private JTabbedPane topLevelPane;
	private JTabbedPane subLevelSearchPane;
	private JTabbedPane subLevelManagePane;
	
	/**
	 * Create the frame.
	 */
	public MainView()
	{
		IMS.getInstance().addObserver(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 630);
		setTitle("ShelfLife");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		
		addWindowListener(m_conFac.createController(ControllerFactory.MAIN_VIEW).getListener("exit"));
		topLevelPane = new JTabbedPane(JTabbedPane.TOP);
		
		subLevelSearchPane = new JTabbedPane(JTabbedPane.TOP);
		subLevelSearchPane.addTab("Keyword", new KeywordSearchPanel(false));
		subLevelSearchPane.addTab("Browse", new BrowseSearchPanel());
		
		subLevelManagePane = new JTabbedPane(JTabbedPane.TOP);
		subLevelManagePane.addTab("Sell", new SellManagePanel());
		subLevelManagePane.addTab("Add/Edit", new KeywordSearchPanel(true));
		subLevelManagePane.addTab("Log", new LogManagePanel());
		subLevelManagePane.addTab("Config.", new ConfigManagePanel(this, m_conFac.createController(ControllerFactory.CONFIG)));
		
		// tab 0
		topLevelPane.addTab("Search", subLevelSearchPane);
		// tab 1
		topLevelPane.addTab("Login", new LoginPanel(this, m_conFac.createController(ControllerFactory.LOGIN)));
		// tab 2
		topLevelPane.addTab("Manage", subLevelManagePane);
		
		contentPane.add(topLevelPane, BorderLayout.CENTER);
		
		updateLoginTab();
		
	}
	
	public void changeState(States state)
	{
		m_state = state;
	}
	
	
	public void updateLoginTab()
	{
		if (m_state == States.LOGGED_OUT)
		{ 
			topLevelPane.setTitleAt(LOGIN_TAB, "Login");
			topLevelPane.setComponentAt(LOGIN_TAB, new LoginPanel(this, m_conFac.createController(ControllerFactory.LOGIN)));
			
			topLevelPane.setBackgroundAt(MANAGE_TAB, Color.DARK_GRAY);
			topLevelPane.setEnabledAt(MANAGE_TAB, false);
		}
		if (m_state == States.LOGGED_IN)
		{
			topLevelPane.setTitleAt(LOGIN_TAB, "Logout");
			topLevelPane.setComponentAt(LOGIN_TAB, new LogoutPanel(this, m_conFac.createController(ControllerFactory.LOGIN)));
			topLevelPane.setBackgroundAt(MANAGE_TAB, Color.LIGHT_GRAY);
			topLevelPane.setEnabledAt(MANAGE_TAB, true);
		}
	}

	public void update(Observable arg0, Object arg1)
	{
		updateLoginTab();
	}

}
