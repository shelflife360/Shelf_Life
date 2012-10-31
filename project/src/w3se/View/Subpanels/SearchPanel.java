package w3se.View.Subpanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JComboBox;

import w3se.Base.Book;
import w3se.Controller.Controller;
import w3se.Controller.ControllerFactory;
import w3se.View.ShelfLifeIcon;

public class SearchPanel extends JPanel
{
	public static final int WIDTH = 540;
	public static final int HEIGHT = 500;
	public static final int HEADER_HEIGHT = 50;
	public static final int KEYWORD_HEADER = 0;
	public static final int BROWSE_HEADER = 1;
	private Controller m_controller = null;
	
	private int m_headerType = KEYWORD_HEADER;
	
	private JTextField m_searchField;
	private List m_resultsList;
	private JList m_prevViewedList;
	private JPanel m_header;
	private JPanel m_body;
	private JLabel m_lblRecentlyViewed;
	private JLabel m_lblResults;
	private JLabel m_lblLogo;
	private JComboBox m_cbGenres;
	private JComboBox m_cbOrder;
	
	/**
	 * Create the panel.
	 */
	public SearchPanel(int headerType, Controller controller)
	{
		m_controller = controller;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		m_headerType = headerType;
		
		createHeader(m_headerType);
		createBody();
		
		setLayout(new BorderLayout());
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		add(splitPane, BorderLayout.CENTER);
		splitPane.setEnabled(false);
		splitPane.setTopComponent(m_header); // set the top panel
		splitPane.setBottomComponent(m_body);
		
	}
	
	private void createHeader(int headerType)
	{
		m_header = new JPanel();
		m_header.setPreferredSize(new Dimension(WIDTH, HEADER_HEIGHT));
		
		JButton searchBtn = new JButton("Search");
		
		if (headerType == KEYWORD_HEADER)
		{
			m_header.setLayout(new FlowLayout());
			JLabel kwSearchLbl = new JLabel("Keyword Search");
			
			m_searchField = new JTextField();
			m_searchField.setColumns(20);
			
			m_header.add(kwSearchLbl);
			m_header.add(m_searchField);
			m_header.add(searchBtn);
			
			searchBtn.addActionListener(m_controller.getListener("search_term"));
			m_searchField.addActionListener(m_controller.getListener("search_term"));
		}
		else if (headerType == BROWSE_HEADER)
		{
			JLabel lblGenres =  new JLabel("Genres");
			JLabel lblOrder = new JLabel("Order");
			
			m_cbGenres = new JComboBox( new String[] {"Technology", "Space", "Asscandy"});
			m_cbOrder = new JComboBox();
			
			GroupLayout gl_m_header = new GroupLayout(m_header);
			gl_m_header.setHorizontalGroup(
				gl_m_header.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_m_header.createSequentialGroup()
						.addGap(22)
						.addComponent(lblGenres)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(m_cbGenres, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblOrder)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(m_cbOrder, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(searchBtn)
						.addContainerGap())
			);
			gl_m_header.setVerticalGroup(
				gl_m_header.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_m_header.createSequentialGroup()
						.addGap(5)
						.addGroup(gl_m_header.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblGenres)
							.addComponent(m_cbGenres, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblOrder)
							.addComponent(m_cbOrder, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(searchBtn)))
			);
			m_header.setLayout(gl_m_header);
		}
	}
	
	private void createBody()
	{
		m_body = new JPanel();
		m_body.setBackground(new Color(255, 255, 255));
		m_body.setPreferredSize(new Dimension(WIDTH, (HEIGHT-HEADER_HEIGHT)));
		m_resultsList = new List();
		m_resultsList.addActionListener(m_controller.getListener("results_list"));
		
		m_prevViewedList = new JList();
		m_prevViewedList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		m_lblRecentlyViewed = new JLabel("Recently Viewed Books");
		
		m_lblResults = new JLabel("Search Results");
		
		m_lblLogo = new JLabel();
		m_lblLogo.setIcon(new ShelfLifeIcon("icon.png"));
		
		JButton btnClearResults = new JButton("Clear Results");
		btnClearResults.addActionListener(m_controller.getListener("results_clear"));
		JButton btnClearRecentlyViewed = new JButton("Clear Recently Viewed");
		btnClearRecentlyViewed.addActionListener(m_controller.getListener("recently_clear"));
		
		GroupLayout gl_m_body = new GroupLayout(m_body);
		gl_m_body.setHorizontalGroup(
			gl_m_body.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_m_body.createSequentialGroup()
					.addGap(16)
					.addGroup(gl_m_body.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_m_body.createSequentialGroup()
							.addGroup(gl_m_body.createParallelGroup(Alignment.LEADING)
								.addComponent(m_resultsList, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(gl_m_body.createSequentialGroup()
									.addGap(6)
									.addGroup(gl_m_body.createParallelGroup(Alignment.LEADING)
										.addComponent(m_lblLogo)
										.addComponent(m_prevViewedList, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE))))
							.addContainerGap())
						.addGroup(gl_m_body.createSequentialGroup()
							.addComponent(m_lblResults)
							.addPreferredGap(ComponentPlacement.RELATED, 226, Short.MAX_VALUE)
							.addComponent(btnClearResults)
							.addGap(86))
						.addGroup(gl_m_body.createSequentialGroup()
							.addComponent(m_lblRecentlyViewed)
							.addPreferredGap(ComponentPlacement.RELATED, 208, Short.MAX_VALUE)
							.addComponent(btnClearRecentlyViewed)
							.addGap(52))))
		);
		gl_m_body.setVerticalGroup(
			gl_m_body.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_m_body.createSequentialGroup()
					.addGap(37)
					.addGroup(gl_m_body.createParallelGroup(Alignment.BASELINE)
						.addComponent(m_lblResults)
						.addComponent(btnClearResults))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(m_resultsList, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
					.addGap(37)
					.addGroup(gl_m_body.createParallelGroup(Alignment.BASELINE)
						.addComponent(m_lblRecentlyViewed)
						.addComponent(btnClearRecentlyViewed))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(m_prevViewedList, GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
					.addGap(75)
					.addComponent(m_lblLogo)
					.addGap(21))
		);
		
		m_body.setLayout(gl_m_body);
	}
	
	/**
	 * method to return the text in the search field
	 * @return
	 */
	public String getSearchText()
	{
		return m_searchField.getText();
	}
	
	/**
	 * method to clear the search bar
	 */
	public void clearSearchText()
	{
		if (m_headerType == KEYWORD_HEADER)
			m_searchField.setText("");
	}
	
	public void setSearchResults(ArrayList<Book> list)
	{
		for (int i = 0; i < list.size(); i++)
		{
			m_resultsList.add(list.get(i).getTitle());
		}
	}
	
	public int getSelectedBookIndex()
	{
		return m_resultsList.getSelectedIndex();
	}
	
	public void clearLists()
	{
		for (int i = 0; i < m_resultsList.getItemCount(); i++)
		{
			m_resultsList.remove(i);
		}
	}
}
