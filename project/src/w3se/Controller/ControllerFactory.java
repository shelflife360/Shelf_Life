package w3se.Controller;

import w3se.Model.IMS;
import w3se.View.Panels.LoginPanel;

public class ControllerFactory
{
	public static final int MAIN_VIEW = 0;
	public static final int KEYWORD_SEARCH = 1;
	public static final int LOGIN = 2;
	public static final int CONFIG = 3;
	private static ControllerFactory m_conFactory = null;
	
	private static IMS m_model = null;
	
	private ControllerFactory()
	{
	}
	
	public static ControllerFactory getInstance()
	{
		if (m_conFactory == null)
			m_conFactory = new ControllerFactory();
		
		return m_conFactory;
	}
	
	public static void registerModel(IMS model)
	{
		m_model = model;
	}
	
	public Controller createController(int controllerType)
	{
		Controller controller = null;
	
		switch (controllerType)
		{
			case MAIN_VIEW:
				controller = new MainViewController(m_model);
				return controller;
				
			
			case KEYWORD_SEARCH:
				controller = new KeywordSearchController(m_model);
				return controller;
				
			case LOGIN:
				controller = new LoginViewController(m_model);
				return controller;
				
			case CONFIG:
				controller = new ConfigViewController(m_model);
				return controller;
				
		}
		
		return controller;
	}
}
