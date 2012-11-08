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

import w3se.Controller.Controller;
import w3se.Model.Configurations;
import w3se.Model.IMS;
import w3se.Model.Base.Book;
import w3se.Model.Base.LogItem;
import w3se.View.ShelfLifeIcon;

import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class LogManagePanel extends JPanel implements Observer
{

	private JComboBox m_cbSearchBy;
	private JComboBox m_cbOrderBy;
	private JLabel m_lblLogo;
	private JTable m_logList;
	private Controller m_controller;
	private JTextField m_searchField;
	
	/**
	 * Create the panel.
	 */
	public LogManagePanel(Controller controller)
	{
		m_controller = controller;
		m_controller.registerView(this);
				
		setBackground(new Color(255, 255, 255));
		this.setBounds(0, 0, 940, 500);
		JLabel lblSearchBy = new JLabel("Search By : ");
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(m_controller.getListener("search_by"));
		
		JButton btnShowAllLogs = new JButton("Show All Logs");
		btnShowAllLogs.addActionListener(m_controller.getListener("search_all"));
				
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
		
		JButton btnClearAll = new JButton("Clear All");
		btnClearAll.addActionListener(m_controller.getListener("clear"));
		
		m_lblLogo = new JLabel();
		m_lblLogo.setIcon(new ShelfLifeIcon(Configurations.SL_ICON));
		
		m_cbSearchBy = new JComboBox(new String[] {"Time", "Action", "ID"});
		
		JLabel lblOrderBy = new JLabel("Order By :");
		
		m_cbOrderBy = new JComboBox(new String[] {"Time", "Action", "ID"});
		
		m_searchField = new JTextField();
		m_searchField.setColumns(10);
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(resultScroll, GroupLayout.PREFERRED_SIZE, 884, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblSearchBy)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(m_cbSearchBy, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(m_searchField, GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(lblOrderBy)
							.addGap(18)
							.addComponent(m_cbOrderBy, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
							.addGap(46)
							.addComponent(btnSearch)
							.addGap(34)))
					.addComponent(btnShowAllLogs))
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(m_lblLogo)
					.addPreferredGap(ComponentPlacement.RELATED, 595, Short.MAX_VALUE)
					.addComponent(btnClearAll)
					.addGap(169))
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
						.addComponent(m_cbOrderBy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblOrderBy)
						.addComponent(m_searchField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(resultScroll, GroupLayout.PREFERRED_SIZE, 384, GroupLayout.PREFERRED_SIZE)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnClearAll)
							.addContainerGap(29, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(m_lblLogo)
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
			
			String[] strings = new String[] {""+log.getID(),log.getTimeStampString(),""+log.getAction(),log.getDesc()};
			
			((DefaultTableModel)m_logList.getModel()).addRow(strings);
		}
	}
	
	public void clearLists()
	{
		while(((DefaultTableModel)m_logList.getModel()).getRowCount() > 0)
		{
			((DefaultTableModel)m_logList.getModel()).removeRow(0);
		}
	}
	
	public int getSearchByIndex()
	{
		return m_cbSearchBy.getSelectedIndex();
	}
	
	public String getSearchTerm()
	{
		return m_searchField.getText();
	}
	
	public int getOrderByIndex()
	{
		return m_cbOrderBy.getSelectedIndex();
	}
	
	@Override
	public void update(Observable arg0, Object arg1)
	{
		//ArrayList<LogItem> items = IMS.getInstance().getLogs(LogItem.ALL);
	}
}
