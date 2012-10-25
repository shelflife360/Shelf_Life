package w3se.View.Subpanels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JButton;

import w3se.Controller.Controller;

public class AddUserPanel extends JPanel
{
	public static final int WIDTH = 450;
	public static final int HEIGHT = 500;
	private JTextField m_usernameField;
	private JPasswordField m_passwordField;
	private JTextField m_uidField;
	private JComboBox m_privileges;
	private Controller m_controller;
	
	/**
	 * Create the panel.
	 */
	public AddUserPanel(Controller controller)
	{
		m_controller = controller;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		m_usernameField = new JTextField();
		m_usernameField.setColumns(20);
		
		JLabel lblUsername = new JLabel("Username :");
		
		JLabel lblPassword = new JLabel("Password :");
		
		m_passwordField = new JPasswordField();
		m_passwordField.setColumns(20);
		
		JLabel lblNewLabel = new JLabel("Privilege :");
		String[] privilegesStrs = {"GENERAL", "WORKER", "MANAGER"};
		m_privileges = new JComboBox(privilegesStrs);
		
		m_uidField = new JTextField();
		m_uidField.setColumns(10);
		
		JLabel lblUserId = new JLabel("User ID :");
		
		JLabel lblAddUser = new JLabel("Add User");
		
		JButton btnClearFields = new JButton("Clear Fields");
		
		JButton btnAddUser = new JButton("Add User");
		
		btnClearFields.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_usernameField.setText("");
						m_passwordField.setText("");
						m_uidField.setText("");
						m_privileges.setSelectedIndex(0);
					}
				});
		
		btnAddUser.addActionListener(m_controller.getListener("add_user"));
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(27)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(lblUsername)
									.addComponent(lblPassword))
								.addComponent(lblNewLabel)
								.addComponent(lblUserId))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(m_uidField, Alignment.LEADING)
								.addComponent(m_privileges, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(m_passwordField, Alignment.LEADING)
								.addComponent(m_usernameField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
								.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
									.addComponent(btnClearFields)
									.addGap(18)
									.addComponent(btnAddUser))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(151)
							.addComponent(lblAddUser)))
					.addContainerGap(84, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(41)
					.addComponent(lblAddUser)
					.addGap(55)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblUsername)
						.addComponent(m_usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(65)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(m_passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPassword))
					.addGap(54)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(m_privileges, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(55)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(m_uidField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUserId))
					.addPreferredGap(ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnClearFields)
						.addComponent(btnAddUser))
					.addGap(27))
		);
		setLayout(groupLayout);

	}
	
	public String getUsername()
	{
		return m_usernameField.getText();
	}
	
	public String getPassword()
	{
		return m_passwordField.getText();
	}
	
	public int getPrivilege()
	{
		return m_privileges.getSelectedIndex();
	}
	
	public int getUID()
	{
		return Integer.parseInt(m_uidField.getText());
	}
}
