package w3se.View.Subpanels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import w3se.Base.User;
import w3se.Controller.Controller;
import w3se.Model.Database.UsersDB;

public class ExportPanel extends JPanel
{

	public static final int WIDTH = 450;
	public static final int HEIGHT = 500;
	public static final int ADD = 0;
	public static final int EDIT = 1;
	private Controller m_controller;
	private int m_state = ADD;
	private User m_user = new User();
	
	/**
	 * Create the panel.
	 */
	public ExportPanel(Controller controller)
	{
		m_controller = controller;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		JLabel lblTitle = new JLabel("Export Manager");
		
		JLabel label = new JLabel("");
		
		JButton btnExportBookDatabase = new JButton("Export Book Database");
		
		JButton btnExportUserDatabase = new JButton("Export User Database");
		
		JButton btnExportLogDatabase = new JButton("Export Log Database");
		
		btnExportBookDatabase.addActionListener(m_controller.getListener("books_export"));
		
		btnExportUserDatabase.addActionListener(m_controller.getListener("users_export"));
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(161)
							.addComponent(lblTitle))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(79)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnExportUserDatabase)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(label)
									.addGap(39)
									.addComponent(btnExportBookDatabase))
								.addComponent(btnExportLogDatabase))))
					.addContainerGap(150, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addComponent(lblTitle)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(73)
							.addComponent(label))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(59)
							.addComponent(btnExportBookDatabase)))
					.addGap(18)
					.addComponent(btnExportUserDatabase)
					.addGap(18)
					.addComponent(btnExportLogDatabase)
					.addContainerGap(282, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
	
}