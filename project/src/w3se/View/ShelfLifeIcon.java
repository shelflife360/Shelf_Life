package w3se.View;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;

public class ShelfLifeIcon implements Icon
{
	private static final int HEIGHT = 64;
	private static final int WIDTH = 64;
	private BufferedImage m_img = null;
	public ShelfLifeIcon(String filename)
	{
		try
		{
			m_img = ImageIO.read(new File(filename));
		} 
		catch (IOException e)									// error
		{
			System.out.println("Error opening image");
		}
	}
	
	public int getIconHeight()
	{
		return HEIGHT;
	}

	public int getIconWidth()
	{
		return WIDTH;
	}

	
	public void paintIcon(Component c, Graphics g, int x, int y)
	{
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.drawImage(m_img, null, y, y);
	}

}
