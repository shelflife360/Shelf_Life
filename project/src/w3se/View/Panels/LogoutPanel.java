package w3se.View.Panels;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import w3se.Controller.Controller;
import w3se.Controller.ControllerFactory;
import w3se.Model.IMS;
import w3se.View.MainView;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

public class LogoutPanel extends JPanel implements Observer
{
	Controller m_controller = null;
	MainView m_parent = null;
	/**
	 * Create the panel.
	 */
	public LogoutPanel(MainView mainView, Object object)
	{
		m_controller = (Controller)object;
		m_parent = mainView;
		
		setBackground(new Color(255, 255, 255));
		setLayout(new BorderLayout(0, 0));
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(m_controller.getListener("logout"));
		
		add(btnLogout, BorderLayout.CENTER);
	}

	public void update(Observable o, Object arg)
	{
		m_parent.changeState(IMS.getInstance().getLoginState());
	}
	

}
