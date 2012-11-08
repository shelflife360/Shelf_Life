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

import w3se.Controller.Controller;
import w3se.Model.IMS;
import w3se.Model.Base.User;
import w3se.Model.Database.UsersDB;

public class ExportPanel extends JPanel
{

	public static final int WIDTH = 450;
	public static final int HEIGHT = 500;
	public static final int EXCEL_EXP = 0;
	public static final int TEXT_EXP = 1;
	
	private Controller m_controller;
	private User m_user = new User();
	private JComboBox m_exportType;
	
	/**
	 * Create the panel.
	 */
	public ExportPanel(Controller controller)
	{
		m_controller = controller;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(IMS.getInstance().getTheme().getSecondaryColor());
		JLabel lblTitle = new JLabel("Export Manager");
		
		JLabel label = new JLabel();
		
		JButton btnExportBookDatabase = new JButton("Export Book Database");
		
		JButton btnExportUserDatabase = new JButton("Export User Database");
		
		JButton btnExportLogDatabase = new JButton("Export Log Database");
		
		btnExportBookDatabase.addActionListener(m_controller.getListener("books_export"));
		
		btnExportUserDatabase.addActionListener(m_controller.getListener("users_export"));
		
		btnExportLogDatabase.addActionListener(m_controller.getListener("logs_export"));
		
		m_exportType = new JComboBox(new String[] {"Excel Exporter", "Plain Text Exporter"});
		
		
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
							.addComponent(label))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(130)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(m_exportType, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnExportLogDatabase)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
									.addComponent(btnExportBookDatabase, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(btnExportUserDatabase, Alignment.LEADING)))))
					.addContainerGap(138, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addComponent(lblTitle)
					.addGap(50)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(label)
						.addComponent(m_exportType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(52)
					.addComponent(btnExportBookDatabase)
					.addGap(34)
					.addComponent(btnExportUserDatabase)
					.addGap(32)
					.addComponent(btnExportLogDatabase)
					.addContainerGap(182, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
	
	public int getExporterSelection()
	{
		return m_exportType.getSelectedIndex();
	}
}