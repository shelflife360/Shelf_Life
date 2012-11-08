package w3se.View.Panels;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import w3se.Controller.Controller;
import w3se.Model.IMS;
import w3se.Model.Base.States;
import w3se.View.MainView;
import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("serial")
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
		
		setBackground(IMS.getInstance().getTheme().getMainColor());
		setLayout(new BorderLayout(0, 0));
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(m_controller.getListener("logout"));
		
		add(btnLogout, BorderLayout.CENTER);
	}

	public void update(Observable o, Object arg)
	{
		States state = (States)arg;
		m_parent.changeState(state);
	}
	

}
