package w3se.View.Subpanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

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
import javax.swing.JComboBox;

import w3se.Controller.Controller;
import w3se.Controller.ControllerFactory;

public class SearchPanel extends JPanel
{
	public static final int WIDTH = 540;
	public static final int HEIGHT = 500;
	public static final int HEADER_HEIGHT = 50;
	public static final int KEYWORD_HEADER = 0;
	public static final int BROWSE_HEADER = 1;
	public static final String SEARCH_FIELD = "search_field";
	public static final String RESULT_LIST = "result_list";
	public static final String PREV_VIEWED_LIST = "prev_viewed_list";
	public static final String GENRES = "genres";
	public static final String DISPLAY_ORDER = "display_order";
	private Controller m_controller = null;
	
	private JTextField m_searchField;
	private JList m_resultsList;
	private JList m_prevViewedList;
	private JPanel m_header;
	private JPanel m_body;
	private JLabel lblRecentlyViewed;
	private JLabel lblResults;
	private JLabel lblLogo;
	private JComboBox cbGenres;
	private JComboBox cbOrder;
	
	/**
	 * Create the panel.
	 */
	public SearchPanel(int headerType)
	{
		//m_controller = ControllerFactory.createController(ControllerFactory.KEYWORD_SEARCH);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	
		createHeader(headerType);
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
		
		if (headerType == KEYWORD_HEADER)
		{
			m_header.setLayout(new FlowLayout());
			JLabel kwSearchLbl = new JLabel("Keyword Search");
			
			m_searchField = new JTextField();
			m_searchField.setColumns(20);
			//m_searchField.addActionListener(m_controller.getListener(SEARCH_FIELD));
			
			m_header.add(kwSearchLbl);
			m_header.add(m_searchField);
		//	m_header.add(searchBtn);
		}
		else if (headerType == BROWSE_HEADER)
		{
			JButton searchBtn = new JButton("Search");
			JLabel lblGenres =  new JLabel("Genres");
			JLabel lblOrder = new JLabel("Order");
			
			
			cbGenres = new JComboBox();
			cbOrder = new JComboBox();
			
			GroupLayout gl_m_header = new GroupLayout(m_header);
			gl_m_header.setHorizontalGroup(
				gl_m_header.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_m_header.createSequentialGroup()
						.addGap(22)
						.addComponent(lblGenres)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(cbGenres, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblOrder)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(cbOrder, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
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
							.addComponent(cbGenres, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblOrder)
							.addComponent(cbOrder, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
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
		m_resultsList = new JList();
		m_resultsList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		m_prevViewedList = new JList();
		m_prevViewedList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		lblRecentlyViewed = new JLabel("Recently Viewed Books");
		
		lblResults = new JLabel("Search Results");
		
		lblLogo = new JLabel("ShelfLife");
		
		GroupLayout gl_m_body = new GroupLayout(m_body);
		gl_m_body.setHorizontalGroup(
			gl_m_body.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_m_body.createSequentialGroup()
					.addGap(16)
					.addGroup(gl_m_body.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_m_body.createSequentialGroup()
							.addGap(6)
							.addComponent(m_prevViewedList, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(m_resultsList, GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
						.addComponent(lblResults)
						.addGroup(gl_m_body.createParallelGroup(Alignment.TRAILING)
							.addComponent(lblLogo, Alignment.LEADING)
							.addComponent(lblRecentlyViewed)))
					.addContainerGap())
		);
		gl_m_body.setVerticalGroup(
			gl_m_body.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_m_body.createSequentialGroup()
					.addGap(37)
					.addComponent(lblResults)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(m_resultsList, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
					.addGap(37)
					.addComponent(lblRecentlyViewed)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(m_prevViewedList, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
					.addGap(42)
					.addComponent(lblLogo)
					.addGap(54))
		);
		
		m_body.setLayout(gl_m_body);
	}
}
