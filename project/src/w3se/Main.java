package w3se;

import java.awt.EventQueue;
import java.io.File;

import w3se.Controller.ControllerFactory;
import w3se.Model.Configurations;
import w3se.Model.IMS;
import w3se.Model.Base.Package;
import w3se.Model.Base.User;
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
		/*IMS.getInstance().fixResource(1);
		User u = new User();
		u.setPassword("");
		System.out.println(u.getPassword());
		System.exit(0);*/
		
		IMS.getInstance();
		
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
