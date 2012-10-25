package w3se.View.Panels;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import w3se.View.Subpanels.BookInfoPanel;
import w3se.View.Subpanels.SearchPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;

public class KeywordSearchPanel extends JPanel
{
	public static final int WIDTH = 1020;
	public static final int HEIGHT = 500;
	public static final String SEARCH_FIELD = "search_field";
	public static final String RESULT_LIST = "result_list";
	public static final String PREV_VIEWED_LIST = "prev_viewed_list";
	public static final String GENRES = "genres";
	public static final String DISPLAY_ORDER = "display_order";
	/**
	 * Create the panel.
	 */
	public KeywordSearchPanel(boolean editable)
	{
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(new BorderLayout());
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setEnabled(true);
		add(splitPane, BorderLayout.CENTER);
		
		SearchPanel mainPanel = new SearchPanel(SearchPanel.KEYWORD_HEADER);
		BookInfoPanel infoPanel = new BookInfoPanel(editable);
		
		splitPane.setLeftComponent(mainPanel);
		splitPane.setRightComponent(infoPanel);

	}
	
	
}
