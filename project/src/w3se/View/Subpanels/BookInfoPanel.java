package w3se.View.Subpanels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import w3se.Controller.Controller;

public class BookInfoPanel extends JPanel
{
	public static final int WIDTH = 450;
	public static final int HEIGHT = 500;
	private Controller m_controller = null;
	private JTextField m_titleField;
	private JTextField m_ISBNField;
	private JTextField m_priceField;
	private JTextField m_authorField;
	private JTextField m_publisherField;
	private JTextPane m_descTextPane;
	private JList genreList;
	
	/**
	 * Create the panel.
	 */
	public BookInfoPanel(boolean editable)
	{
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		JLabel lblBookInformation = new JLabel("Book Information");
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.WHITE);
		
		JLabel lblTitle = new JLabel("Title:");
		
		JLabel lblISBN = new JLabel("ISBN:");
		
		JLabel lblPrice = new JLabel("Price:");
		
		JLabel lblAuthor = new JLabel("Author:");
		
		JLabel lblPublisher = new JLabel("Publisher:");
		
		JLabel lblCategories = new JLabel("Genres:");
		
		JLabel lblDesc = new JLabel("Description:");
		
		m_titleField = new JTextField();
		m_titleField.setEditable(editable);
		m_titleField.setColumns(14);
		
		m_ISBNField = new JTextField();
		m_ISBNField.setEditable(editable);
		m_ISBNField.setColumns(14);
		
		m_priceField = new JTextField();
		m_priceField.setEditable(editable);
		m_priceField.setColumns(14);
		
		m_authorField = new JTextField();
		m_authorField.setEditable(editable);
		m_authorField.setColumns(13);
		
		m_publisherField = new JTextField();
		m_publisherField.setEditable(editable);
		m_publisherField.setColumns(11);
		
		m_descTextPane = new JTextPane();
		m_descTextPane.setEditable(editable);
		genreList = new JList();
		
		GroupLayout gl_infoPanel = new GroupLayout(this);
		gl_infoPanel.setHorizontalGroup(
			gl_infoPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addGap(17)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addComponent(lblDesc)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(m_descTextPane, GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE))
						.addComponent(lblAuthor)
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addComponent(lblPrice)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(m_priceField, GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE))
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addComponent(lblISBN)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(m_ISBNField, GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE))
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addComponent(lblTitle)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(m_titleField, GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE))
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPublisher)
								.addComponent(lblCategories))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_infoPanel.createSequentialGroup()
									.addGap(6)
									.addComponent(genreList, GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE))
								.addGroup(gl_infoPanel.createSequentialGroup()
									.addGap(6)
									.addComponent(m_publisherField, GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE))
								.addComponent(m_authorField, GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE))))
					.addContainerGap())
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addContainerGap(290, Short.MAX_VALUE)
					.addComponent(lblBookInformation)
					.addGap(155))
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(separator, GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_infoPanel.setVerticalGroup(
			gl_infoPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addGap(17)
					.addComponent(lblBookInformation)
					.addGap(18)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTitle)
						.addComponent(m_titleField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblISBN)
						.addComponent(m_ISBNField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblPrice)
						.addComponent(m_priceField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAuthor)
						.addComponent(m_authorField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPublisher)
						.addComponent(m_publisherField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCategories)
						.addComponent(genreList, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDesc)
						.addComponent(m_descTextPane, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))
					.addContainerGap())
		);
		setLayout(gl_infoPanel);
	}

}
