package w3se.View.Panels;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import w3se.Controller.Controller;
import w3se.Model.IMS;
import w3se.View.MainView;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("serial")
public class LoginPanel extends JPanel implements Observer
{
	private JTextField userNameField;
	private JPasswordField passwordField;
	private Controller m_controller = null;
	private MainView m_mainView = null;
	private JButton m_btnLogin = null;
	/**
	 * Create the panel.
	 */
	public LoginPanel(MainView mainView, Controller controller)
	{
		m_mainView = mainView;
		
		m_controller = controller;
		
		IMS.getInstance().addObserver(this);
		setBackground(new Color(255, 255, 255));
		
		JLabel lblUserName = new JLabel("User Name :");
		
		userNameField = new JTextField();
		userNameField.setColumns(20);
		
		JLabel lblPassword = new JLabel("Password : ");
		
		passwordField = new JPasswordField();
		passwordField.setColumns(20);
		
		JButton btnLogin = new JButton("Login");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(182)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblUserName, Alignment.TRAILING)
								.addComponent(lblPassword, Alignment.TRAILING))
							.addGap(49)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(userNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(329)
							.addComponent(btnLogin)))
					.addContainerGap(175, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(52)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUserName)
						.addComponent(userNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPassword))
					.addGap(74)
					.addComponent(btnLogin)
					.addContainerGap(233, Short.MAX_VALUE))
		);
		
		btnLogin.addActionListener(m_controller.getListener("login"));
		btnLogin.setActionCommand(userNameField.getText());
		setLayout(groupLayout);
		m_controller.registerView(this);
	}

	public void setUserName(String name)
	{
		userNameField.setText(name);
	}
	
	public void setPassword(String pass)
	{
		passwordField.setText(pass);
	}
	
	public String getUsername()
	{
		return userNameField.getText();
	}
	
	public String getPassword()
	{
		return passwordField.getText();
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		m_mainView.changeState(IMS.getInstance().getLoginState());
	}
}