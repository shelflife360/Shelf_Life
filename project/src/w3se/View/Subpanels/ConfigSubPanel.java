package w3se.View.Subpanels;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JToggleButton;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import w3se.Controller.Controller;
import w3se.View.Panels.ConfigManagePanel;

@SuppressWarnings("serial")
public class ConfigSubPanel extends JPanel
{
	public static final int WIDTH = 450;
	public static final int HEIGHT = 500;
	private ConfigManagePanel m_parent = null;
	private Controller m_controller = null;
	
	/**
	 * Create the panel.
	 */
	public ConfigSubPanel(ConfigManagePanel parent, Controller controller)
	{
		
		m_parent = parent;
		m_controller = controller;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		final JToggleButton[] buttons = new JToggleButton[4];
		
		buttons[0] = new JToggleButton("Add User");
		
		buttons[2]  = new JToggleButton("Remove User");
		
		buttons[1] = new JToggleButton("Edit User");
		
		buttons[3] = new JToggleButton("SQL Configurations");
		
		JButton btnLogPreferences = new JButton("Log Preferences");
		
		buttons[0].addActionListener(new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if (buttons[0].isSelected())
						{
							m_parent.setInfoPanel(ConfigManagePanel.USER_ADD);
							toggleButtons(buttons, 0);
						}
						else
							m_parent.setInfoPanel(-1);
					}
			
				});
		
		buttons[1].addActionListener(new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if (buttons[1].isSelected())
						{
							m_parent.setInfoPanel(ConfigManagePanel.USER_EDIT);
							toggleButtons(buttons, 1);
						}
						else
							m_parent.setInfoPanel(-1);
					}
			
				});
		
		buttons[2].addActionListener(new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if (buttons[2].isSelected())
						{
							m_parent.setInfoPanel(ConfigManagePanel.USER_REMOVE);
							toggleButtons(buttons, 2);
						}
						else
							m_parent.setInfoPanel(-1);
					}
			
				});
		
		buttons[3].addActionListener(new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if (buttons[3].isSelected())
						{
							m_parent.setInfoPanel(ConfigManagePanel.SQL_CONFIG);
							toggleButtons(buttons, 3);
						}
						else
							m_parent.setInfoPanel(-1);
					}
			
				});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(105)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnLogPreferences)
						.addComponent(buttons[1])
						.addComponent(buttons[0])
						.addComponent(buttons[2])
						.addComponent(buttons[3])
					//.addContainerGap(179, Short.MAX_VALUE))
		)));
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(36)
					.addComponent(buttons[0])
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(buttons[1])
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(buttons[2])
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(buttons[3])
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnLogPreferences)
					.addContainerGap(71, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
	
	private void toggleButtons(JToggleButton[] buttons, int unchangedIndex)
	{
		for (int i = 0; i < buttons.length; i++)
		{
			if (i != unchangedIndex)
				buttons[i].setSelected(false);
		}
	}
}
