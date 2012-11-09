package w3se.View.Subpanels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import w3se.Controller.Controller;
import w3se.Model.IMS;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

public class GeneralSettingsPanel extends JPanel
{

	public static final int WIDTH = 450;
	public static final int HEIGHT = 500;
	
	private Controller m_controller;
	private JCheckBox chcDisplayErrorDialogs;
	private JCheckBox chckbxNotify;
	
	/**
	 * Create the panel.
	 */
	public GeneralSettingsPanel(Controller controller)
	{
		m_controller = controller;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(IMS.getInstance().getTheme().getSecondaryColor());
		JLabel lblGeneralSettings = new JLabel("General Settings");
		
		chcDisplayErrorDialogs = new JCheckBox("Display Error Dialogs");
		chcDisplayErrorDialogs.addActionListener(m_controller.getListener("dialog_toggle"));
		chckbxNotify = new JCheckBox("Notify when in server or client mode");
		chckbxNotify.addActionListener(m_controller.getListener("notify_smode_toggle"));
		
		JButton btnSelectMainColor = new JButton("Select Main Color");
		btnSelectMainColor.addActionListener(m_controller.getListener("select_main_color"));
		
		JButton btnSelectSecondaryColor = new JButton("Select Secondary Color");
		btnSelectSecondaryColor.addActionListener(m_controller.getListener("select_secondary_color"));
		
		JLabel lblLookAndFeel = new JLabel("Look and Feel");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(165)
					.addComponent(lblGeneralSettings)
					.addContainerGap(183, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(80, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnSelectMainColor)
						.addComponent(btnSelectSecondaryColor)
						.addComponent(chckbxNotify)
						.addComponent(chcDisplayErrorDialogs))
					.addGap(109))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(112)
					.addComponent(lblLookAndFeel)
					.addContainerGap(251, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(24)
					.addComponent(lblGeneralSettings)
					.addGap(61)
					.addComponent(chcDisplayErrorDialogs)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chckbxNotify)
					.addGap(37)
					.addComponent(lblLookAndFeel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSelectMainColor)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSelectSecondaryColor)
					.addContainerGap(218, Short.MAX_VALUE))
		);
		setLayout(groupLayout);	
	}
	
	public void updateColor()
	{
		setBackground(IMS.getInstance().getTheme().getSecondaryColor());
	}
	
	public void setDialogCheckBox(boolean value)
	{
		chcDisplayErrorDialogs.setSelected(value);
	}
	
	public boolean getDialogCBSelection()
	{
		return chcDisplayErrorDialogs.isSelected();
	}
	
	public void setNotifyCheckBox(boolean value)
	{
		chckbxNotify.setSelected(value);
	}
	
	public boolean getNotifyUserSelection()
	{
		return chckbxNotify.isSelected();
	}
	
}
