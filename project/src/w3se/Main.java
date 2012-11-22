package w3se;

import java.awt.EventQueue;
import java.io.File;

import w3se.Controller.ControllerFactory;
import w3se.Model.IMS;
import w3se.View.MainView;
import w3se.View.SplashScreen;

public class Main
{
	/**
	 * Launch the application.
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{	
		SplashScreen splash = new SplashScreen(new File("resources/W3SE.jpg").getAbsolutePath());
		splash.run();
		
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainView frame = new MainView();
					
					frame.setVisible(true);
					
				} 
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
