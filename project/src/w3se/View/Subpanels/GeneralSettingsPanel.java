package w3se.View.Subpanels;

import java.awt.Dimension;

import javax.swing.JPanel;

import w3se.Controller.Controller;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.LayoutStyle.ComponentPlacement;

public class GeneralSettingsPanel extends JPanel
{

	public static final int WIDTH = 450;
	public static final int HEIGHT = 500;
	
	private Controller m_controller;
	private JCheckBox chcDisplayErrorDialogs;
	/**
	 * Create the panel.
	 */
	public GeneralSettingsPanel(Controller controller)
	{
		m_controller = controller;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		JLabel lblGeneralSettings = new JLabel("General Settings");
		
		chcDisplayErrorDialogs = new JCheckBox("Display Error Dialogs");
		chcDisplayErrorDialogs.addActionListener(m_controller.getListener("dialog_toggle"));
		JCheckBox chckbxNotifyWhenIn = new JCheckBox("Notify when in server or client mode");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(176, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(chckbxNotifyWhenIn)
						.addComponent(chcDisplayErrorDialogs))
					.addGap(109))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(165)
					.addComponent(lblGeneralSettings)
					.addContainerGap(183, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(24)
					.addComponent(lblGeneralSettings)
					.addGap(61)
					.addComponent(chcDisplayErrorDialogs)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chckbxNotifyWhenIn)
					.addContainerGap(341, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		
		
		
	}
}
