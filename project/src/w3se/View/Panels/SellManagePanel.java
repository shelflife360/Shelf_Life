package w3se.View.Panels;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JTextPane;

import w3se.Base.Book;
import w3se.Controller.Controller;
import w3se.Model.IMS;
import w3se.View.Subpanels.ReceiptInfoPanel;
import w3se.View.Subpanels.SearchPanel;

import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class SellManagePanel extends JPanel implements Observer
{
	private Controller m_controller;
	private SearchPanel m_mainPanel;
	private ReceiptInfoPanel m_infoPanel;
	/**
	 * Create the panel.
	 */
	public SellManagePanel(Controller controller)
	{
		m_controller = controller;
		m_controller.registerView(this);
		
		setBounds(0, 0, 940, 500);
		setLayout(new BorderLayout());
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		
		add(splitPane, BorderLayout.CENTER);
		
		m_mainPanel = new SearchPanel(SearchPanel.KEYWORD_HEADER, controller);
		m_infoPanel = new ReceiptInfoPanel(controller);
		
		splitPane.setLeftComponent(m_mainPanel);
		splitPane.setRightComponent(m_infoPanel);
		IMS.getInstance().addView(this);
	}
	
	/**
	 * method to retrieve the search term from the form
	 * @param searchTerm
	 */
	public void setSearchTerm(String searchTerm)
	{
		
	}
	
	/**
	 * method to retrieve the search term from the form
	 * @param searchTerm
	 * return 
	 */
	public String getSearchTerm()
	{
		return m_mainPanel.getSearchText();
	}

	
	public void update(Observable sender, Object obj)
	{
		ArrayList<Book> list = IMS.getInstance().getListOfFoundBooks();
		ArrayList<Book> soldList = IMS.getInstance().getListofSoldBooks();
		
		m_mainPanel.clearSearchText();
		m_mainPanel.clearLists();
		m_mainPanel.setSearchResults(list);
		
		m_infoPanel.clearReceipt();
		m_infoPanel.setReceiptList(soldList);
		
		double total = 0.0;
		for (int i = 0; i < soldList.size(); i++)
		{
			total += soldList.get(i).getPrice();
		}
		m_infoPanel.setTotalPrice(total);
	}
}