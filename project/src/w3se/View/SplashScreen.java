package w3se.View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class SplashScreen extends JFrame
{

	private JPanel contentPane;

	/**
	 * Create the frame.
	 * @throws InterruptedException 
	 */
	public SplashScreen(final String filename) 
	{
		init(filename);
		
		long startTime = System.currentTimeMillis();
		while (System.currentTimeMillis()-startTime < 2000)
		{}
		dispose();
		
	}
	
	private void init(String filename)
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		BufferedImage img = null;
		
		try
		{
			img = ImageIO.read(new File(filename));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		JLabel lblLogo = new JLabel();
		lblLogo.setIcon(new ImageIcon(img));
		
		contentPane.add(lblLogo, BorderLayout.CENTER);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

}
