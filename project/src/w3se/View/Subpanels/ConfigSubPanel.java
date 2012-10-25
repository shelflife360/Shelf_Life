package w3se.View.Subpanels;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ConfigSubPanel extends JPanel
{

	/**
	 * Create the panel.
	 */
	public ConfigSubPanel()
	{
		
		JButton btnNewButton = new JButton("Add User");
		
		JButton btnRemoveUser = new JButton("Remove User");
		
		JButton btnEditUser = new JButton("Edit User");
		
		JButton btnSqlConfigurations = new JButton("SQL Configurations");
		
		JButton btnLogPreferences = new JButton("Log Preferences");
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(105)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnLogPreferences)
						.addComponent(btnEditUser)
						.addComponent(btnNewButton)
						.addComponent(btnRemoveUser)
						.addComponent(btnSqlConfigurations))
					.addContainerGap(179, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(36)
					.addComponent(btnNewButton)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnEditUser)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnRemoveUser)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnSqlConfigurations)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnLogPreferences)
					.addContainerGap(71, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
}
