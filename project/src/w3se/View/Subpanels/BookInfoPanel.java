package w3se.View.Subpanels;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import w3se.Controller.Controller;
import w3se.Model.IMS;

import javax.swing.JButton;

@SuppressWarnings("serial")
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
	private JTextField m_genreList;
	private JSpinner m_quantity;
	
	/**
	 * Create the panel.
	 */
	public BookInfoPanel(boolean editable, Controller controller)
	{
		m_controller = controller;
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(IMS.getInstance().getTheme().getSecondaryColor());
		
		JLabel lblBookInformation = new JLabel("Book Information");
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.WHITE);
		
		JLabel lblTitle = new JLabel("Title:");
		
		JLabel lblISBN = new JLabel("ISBN:");
		
		JLabel lblPrice = new JLabel("Price: $");
		
		JLabel lblAuthor = new JLabel("Author:");
		
		JLabel lblPublisher = new JLabel("Publisher:");
		
		JLabel lblCategories = new JLabel("Genres:");
		
		JLabel lblDesc = new JLabel("Description:");
		
		JLabel lblQuantity = new JLabel("Quantity:");
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
		
		m_genreList = new JTextField();
		m_genreList.setColumns(13);
		m_genreList.setEditable(editable);
		
		m_quantity = new JSpinner();
		m_quantity.setEnabled(editable);
		
		JScrollPane genrePane = new JScrollPane();
		
		genrePane.add(m_genreList);
		
		JButton btnClear = new JButton("Clear");
		
		JButton btnAccept = new JButton("Accept");
		
		btnClear.addActionListener(m_controller.getListener("info_clear"));
		btnAccept.addActionListener(m_controller.getListener("info_accept"));
		
		GroupLayout gl_infoPanel = new GroupLayout(this);
		gl_infoPanel.setHorizontalGroup(
			gl_infoPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addGap(17)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblAuthor)
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblPrice)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(m_priceField, GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE))
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblQuantity)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(m_quantity, GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE))
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addComponent(lblISBN)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(m_ISBNField, GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addComponent(lblTitle)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(m_titleField, GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPublisher)
								.addComponent(lblCategories))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_infoPanel.createSequentialGroup()
									.addGap(6)
									.addComponent(m_genreList, GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE))
								.addGroup(gl_infoPanel.createSequentialGroup()
									.addGap(6)
									.addComponent(m_publisherField, GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE))
								.addComponent(m_authorField, GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)))
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addComponent(lblDesc)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_infoPanel.createSequentialGroup()
									.addComponent(btnClear)
									.addGap(36)
									.addComponent(btnAccept))
								.addComponent(m_descTextPane, GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE))))
					.addContainerGap())
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addContainerGap(186, Short.MAX_VALUE)
					.addComponent(lblBookInformation)
					.addGap(155))
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(separator, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
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
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(m_priceField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPrice))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(m_quantity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblQuantity))
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
						.addComponent(m_genreList, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDesc)
						.addComponent(m_descTextPane, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnClear)
						.addComponent(btnAccept))
					.addGap(16))
		);
		setLayout(gl_infoPanel);
		
		if (editable == false)
		{
			btnClear.setEnabled(false);
			btnAccept.setEnabled(false);
			btnClear.setVisible(false);
			btnAccept.setVisible(false);
		}
	}

	public String getTitle()
	{
		return m_titleField.getText();
	}

	public void setTitle(String str)
	{
		m_titleField.setText(str);
	}

	public String getISBN()
	{
		return m_ISBNField.getText();
	}

	public void setISBN(String str)
	{
		m_ISBNField.setText(str);
	}

	public String getPrice()
	{
		return m_priceField.getText();
	}

	public void setPrice(String str)
	{
		m_priceField.setText(str);
	}

	public String getAuthor()
	{
		return m_authorField.getText();
	}

	public void setAuthor(String str)
	{
		m_authorField.setText(str);
	}

	public String getPublisher()
	{
		return m_publisherField.getText();
	}

	public void setPublisher(String str)
	{
		m_publisherField.setText(str);
	}

	public String getDesc()
	{
		return m_descTextPane.getText();
	}

	public void setDesc(String str)
	{
		m_descTextPane.setText(str);
	}

	public String getGenreList()
	{
		return m_genreList.getText();
	}

	public void setGenreList(String genreList)
	{
		m_genreList.setText(genreList);
	}
	
	public int getQuantity()
	{
		return (Integer)m_quantity.getValue();
	}
	
	public void setQuantity(int value)
	{
		m_quantity.setValue(value);
	}
}
