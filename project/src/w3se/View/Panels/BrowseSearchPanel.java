package w3se.View.Panels;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import w3se.Controller.Controller;
import w3se.View.Subpanels.BookInfoPanel;
import w3se.View.Subpanels.SearchPanel;


public class BrowseSearchPanel extends JPanel implements Observer
{
	public static final int WIDTH = 1020;
	public static final int HEIGHT = 500;
	/**
	 * Create the panel.
	 */
	public BrowseSearchPanel()
	{
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(new BorderLayout());
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setEnabled(true);
		add(splitPane, BorderLayout.CENTER);
		
		SearchPanel mainPanel = new SearchPanel(SearchPanel.BROWSE_HEADER);
		BookInfoPanel infoPanel = new BookInfoPanel(false);
		
		splitPane.setLeftComponent(mainPanel);
		splitPane.setRightComponent(infoPanel);
	}
	@Override
	public void update(Observable o, Object arg)
	{
		// TODO Auto-generated method stub
		
	}
}
