package w3se.Model.Base;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;

import w3se.Model.Exportable;

public class Receipt
{
	private String m_name;
	private String m_phoneNumber;
	private String m_message;
	private String m_slogan;
	private ArrayList<Book> m_books;
	private NumberFormat m_dollarFormat = NumberFormat.getCurrencyInstance(Locale.US);
	private Graphics2D m_graphics;
	private BufferedImage m_image;
	private int m_cursor = 40;
	private static final int WIDTH = 600;
	private static final int HEIGHT = 840;
	private int m_offset = 0;
	
	public Receipt()
	{
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		
		
		m_image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		m_image = gc.createCompatibleImage(WIDTH, HEIGHT, Transparency.OPAQUE);
		
		m_graphics = (Graphics2D)m_image.getGraphics();
		
		m_offset = (int)m_graphics.getFontMetrics().getHeight() + 10;
	}
	
	public void setStoreName(String name)
	{
		m_name = name;
	}
	
	public void setStorePhoneNumber(String number)
	{
		m_phoneNumber = number;
	}
	
	public void setMessageToRecipient(String message)
	{
		m_message = message;
	}
	
	public void setSlogan(String slogan)
	{
		m_slogan = slogan;
	}
	
	public String getStoreName()
	{
		return m_name;
	}
	
	public String getStorePhoneNumber()
	{
		return m_phoneNumber;
	}
	
	public String getMessageToRecipient()
	{
		return m_message;
	}
	
	public String getSlogan()
	{
		return m_slogan;
	}
	
	public void printReceipt(ArrayList<Book> list) throws Exception
	{	
		m_books = list;
		
		fillImage(Color.WHITE);
		
		createHeader();
		createBody();
		createFooter();
		
		File output = new File("receipt.png");
	
		try
		{
			ImageIO.write(m_image, "png", output);
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		File file = new File("receipt.png");
		
		if (Desktop.isDesktopSupported())
		{
			Desktop.getDesktop().print(file);
		}
		
		/*PrintRequestAttributeSet printReqAttSet = new HashPrintRequestAttributeSet();
		printReqAttSet.add(new Copies(1));
		PrintService pss[] = PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.PNG, null);
		if (pss.length == 0)
			throw new Exception("Error printing receipt");
		PrintService printServ = pss[0];
		DocPrintJob printJob = printServ.createPrintJob();
		FileInputStream fin = new FileInputStream("receipt.png");
		Doc doc = new SimpleDoc(fin, DocFlavor.INPUT_STREAM.PNG, null);
		printJob.print(doc, printReqAttSet);
		fin.close();*/
		m_cursor = 40;
	}
	
	private void createHeader()
	{
		drawStringCenterAligned(m_name, 0, m_cursor, Color.BLACK);
		m_cursor += m_offset;
		drawStringCenterAligned(m_phoneNumber, 0, m_cursor, Color.BLACK);
		m_cursor += m_offset;
		drawStringCenterAligned(new Date().toString(), 0, m_cursor, Color.BLACK);
		m_cursor += m_offset;
	}
	
	private void createBody()
	{
		m_cursor += m_offset + 30;
		
		float total = 0.0f;
		
		for (int i = 0; i < m_books.size(); i++)
		{	
			m_graphics.drawString(m_books.get(i).getTitle(), 10, m_cursor);
			m_graphics.drawString(m_dollarFormat.format(m_books.get(i).getPrice()), 450, m_cursor);
			total += m_books.get(i).getPrice();
			m_cursor += m_offset;
		}
		
		m_cursor += m_offset + 40;
		m_graphics.drawString("Sub Total : ", 350, m_cursor);
		m_graphics.drawString(m_dollarFormat.format(total), 450, m_cursor);
		m_cursor += m_offset;
		m_graphics.drawString("Tax : ", 350, m_cursor);
		m_graphics.drawString(m_dollarFormat.format(total*(0.05)), 450, m_cursor);
		m_cursor += m_offset;
		total += total*(0.05);
		m_graphics.drawString("Total :", 350, m_cursor);
		m_graphics.drawString(m_dollarFormat.format(total), 450, m_cursor);
		m_cursor += m_offset + 20;
	}
	
	private void createFooter()
	{
		drawStringCenterAligned(m_message, 0, m_cursor, Color.BLACK);
		m_cursor += m_offset;
		drawStringCenterAligned(m_slogan, 0, m_cursor, Color.BLACK);
		m_cursor += m_offset;
	}
	
	private void drawStringCenterAligned(String str, int startX, int startY, Color color)
	{
		m_graphics.setColor(color);
		int stringLen = (int)m_graphics.getFontMetrics().getStringBounds(str, m_graphics).getWidth();  
	    int start = WIDTH/2 - stringLen/2;  // width/2 - "" 
	    m_graphics.drawString(str, start - startX, startY);
	}
	
	private void fillImage(Color color)
	{
		m_graphics.setColor(color);
		m_graphics.fillRect(0, 0, WIDTH, HEIGHT);
	}
}
