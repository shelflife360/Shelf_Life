package w3se.View.Panels;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import w3se.Base.Book;
import w3se.Base.LogItem;
import w3se.Model.IMS;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class LogManagePanel extends JPanel implements Observer
{

	private JComboBox m_cbSearchBy;
	private JComboBox m_cbOrderBy;
	private JTable m_logList;
	
	/**
	 * Create the panel.
	 */
	public LogManagePanel()
	{
		setBackground(new Color(255, 255, 255));
		this.setBounds(0, 0, 940, 500);
		JLabel lblSearchBy = new JLabel("Search By : ");
		
		JButton btnSearch = new JButton("Search");
		
		JButton btnShowAllLogs = new JButton("Show All Logs");
		btnShowAllLogs.addActionListener(new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						IMS.getInstance().getLogs(LogItem.ALL);
						m_view.
					}
				});
		// create an anonymous class to set the cells as not editable
			DefaultTableModel tableModel = new DefaultTableModel(new String[] {"ID","Time", "Action", "Description"}, 0)
				{
					public boolean isCellEditable(int row, int height)
					{
						return false;
					}
				};
					
				m_logList = new JTable(tableModel);
				m_logList.setBorder(new EtchedBorder());
				
				JScrollPane resultScroll = new JScrollPane(m_logList);
		
		JButton btnRemoveEntry = new JButton("Remove Entry");
		
		JButton btnClearAll = new JButton("Clear All");
		
		JButton btnExport = new JButton("Export");
		
		JLabel lblShelflife = new JLabel("ShelfLife");
		
		m_cbSearchBy = new JComboBox(new String[] {"Time", "Action", "ID"});
		
		JLabel lblOrderBy = new JLabel("Order By :");
		
		m_cbOrderBy = new JComboBox(new String[] {"Time", "Action", "ID"});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblSearchBy)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(m_cbSearchBy, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE)
							.addGap(50)
							.addComponent(lblOrderBy)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(m_cbOrderBy, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnSearch)
							.addGap(54)
							.addComponent(btnShowAllLogs))
						.addComponent(resultScroll, GroupLayout.PREFERRED_SIZE, 884, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(50, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblShelflife)
					.addPreferredGap(ComponentPlacement.RELATED, 485, Short.MAX_VALUE)
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
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSearchBy)
						.addComponent(m_cbSearchBy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnShowAllLogs)
						.addComponent(btnSearch)
						.addComponent(lblOrderBy)
						.addComponent(m_cbOrderBy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(resultScroll, GroupLayout.PREFERRED_SIZE, 384, GroupLayout.PREFERRED_SIZE)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnRemoveEntry)
								.addComponent(btnClearAll)
								.addComponent(btnExport))
							.addContainerGap(20, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblShelflife)
							.addGap(19))))
		);
		setLayout(groupLayout);
		IMS.getInstance().addView(this);
	}

	public void setSearchResults(ArrayList<LogItem> list)
	{
		for (int i = 0; i < list.size(); i++)
		{
			LogItem log = list.get(i);
			
			String[] strings = new String[] {""+log.getID(),log.getTimeStamp(),""+log.getAction(),log.getDesc()};
			
			
			((DefaultTableModel)m_logList.getModel()).addRow(strings);
		}
	}
	@Override
	public void update(Observable arg0, Object arg1)
	{
		ArrayList<LogItem> items = IMS.getInstance().getLogs(LogItem.ALL);
		
		
	}
	
	
}
