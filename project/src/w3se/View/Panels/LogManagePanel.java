package w3se.View.Panels;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.border.LineBorder;

import w3se.Model.IMS;

public class LogManagePanel extends JPanel implements Observer
{
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public LogManagePanel()
	{
		setBackground(new Color(255, 255, 255));
		this.setBounds(0, 0, 940, 500);
		JLabel lblNewLabel = new JLabel("Search Term : ");
		
		textField = new JTextField();
		textField.setColumns(20);
		
		JButton btnSearch = new JButton("Search");
		
		JButton btnShowAllLogs = new JButton("Show All Logs");
		
		JList list = new JList();
		list.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JButton btnRemoveEntry = new JButton("Remove Entry");
		
		JButton btnClearAll = new JButton("Clear All");
		
		JButton btnExport = new JButton("Export");
		
		JLabel lblShelflife = new JLabel("ShelfLife");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(56)
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 282, GroupLayout.PREFERRED_SIZE)
							.addGap(26)
							.addComponent(btnSearch)
							.addGap(18)
							.addComponent(btnShowAllLogs))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(list, GroupLayout.PREFERRED_SIZE, 884, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(50, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addComponent(lblShelflife)
					.addPreferredGap(ComponentPlacement.RELATED, 478, Short.MAX_VALUE)
					.addComponent(btnRemoveEntry)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnClearAll)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnExport)
					.addGap(72))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(7)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnSearch)
							.addComponent(btnShowAllLogs))
						.addComponent(lblNewLabel))
					.addGap(18)
					.addComponent(list, GroupLayout.PREFERRED_SIZE, 384, GroupLayout.PREFERRED_SIZE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnRemoveEntry)
								.addComponent(btnClearAll)
								.addComponent(btnExport))
							.addContainerGap(20, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblShelflife)
							.addGap(19))))
		);
		setLayout(groupLayout);
		IMS.getInstance().addView(this);
	}

	@Override
	public void update(Observable arg0, Object arg1)
	{
		// TODO Auto-generated method stub
		
	}
}
