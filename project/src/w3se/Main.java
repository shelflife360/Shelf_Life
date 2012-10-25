package w3se;

import java.awt.EventQueue;

import w3se.Base.User;
import w3se.Controller.ControllerFactory;
import w3se.Model.IMS;
import w3se.Model.Database.DatabaseAdaptor;
import w3se.View.MainView;

public class Main
{
	/**
	 * Launch the application.
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{	
		ControllerFactory.getInstance().registerModel(IMS.getInstance());
		
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainView frame = new MainView();
					
					frame.setVisible(true);
					
					
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
