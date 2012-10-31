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
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JTextPane;

import w3se.View.Subpanels.ReceiptInfoPanel;
import w3se.View.Subpanels.SearchPanel;

import java.awt.SystemColor;

public class SellManagePanel extends JPanel
{
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public SellManagePanel()
	{
		this.setBounds(0, 0, 940, 500);
		setLayout(new BorderLayout());
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		
		add(splitPane, BorderLayout.CENTER);
		
		SearchPanel mainPanel = new SearchPanel(SearchPanel.KEYWORD_HEADER);
		ReceiptInfoPanel infoPanel = new ReceiptInfoPanel();
		
		splitPane.setLeftComponent(mainPanel);
		splitPane.setRightComponent(infoPanel);
		
	}
}