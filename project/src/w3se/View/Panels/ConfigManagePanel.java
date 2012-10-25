package w3se.View.Panels;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import w3se.Controller.Controller;
import w3se.View.MainView;
import w3se.View.Subpanels.AddUserPanel;
import w3se.View.Subpanels.ConfigSubPanel;

public class ConfigManagePanel extends JPanel
{
	public static final int WIDTH = 1020;
	public static final int HEIGHT = 500;
	private MainView m_parent = null;
	private Controller m_controller = null;
	private AddUserPanel m_infoPanel = null;
	
	/**
	 * Create the panel.
	 */
	public ConfigManagePanel(MainView mainView, Controller controller)
	{
		m_parent = mainView;
		m_controller = controller;
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(new BorderLayout());
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setEnabled(true);
		add(splitPane, BorderLayout.CENTER);
			
		ConfigSubPanel mainPanel = new ConfigSubPanel();
		m_infoPanel = new AddUserPanel(m_controller);
			
		splitPane.setLeftComponent(mainPanel);
		splitPane.setRightComponent(m_infoPanel);
		m_controller.registerView(this);
	}

	public String getUsername()
	{
		return m_infoPanel.getUsername();
	}
	
	public String getPassword()
	{
		return m_infoPanel.getPassword();
	}
	
	public int getPrivilege()
	{
		return m_infoPanel.getPrivilege();
	}
	
	public int getUID()
	{
		return m_infoPanel.getUID();
	}
}
