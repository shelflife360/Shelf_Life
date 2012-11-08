package w3se.Controller;

import w3se.Model.IMS;

public class ControllerFactory
{
	public static final int MAIN_VIEW = 0;
	public static final int SEARCH = 1;
	public static final int LOGIN = 2;
	public static final int CONFIG = 3;
	public static final int BROWSE = 4;
	public static final int SELL = 5;
	public static final int LOGS = 6;
	
	private static ControllerFactory m_conFactory = null;
	
	private static IMS m_model = null;
	
	private ControllerFactory()
	{
		m_model = IMS.getInstance();
	}
	
	private ControllerFactory(IMS model)
	{
		m_model = model;
	}
	
	public static ControllerFactory getInstance()
	{
		if (m_conFactory == null)
			m_conFactory = new ControllerFactory();
		
		return m_conFactory;
	}
	
	public static ControllerFactory getInstance(IMS model)
	{
		if (m_conFactory == null)
			m_conFactory = new ControllerFactory(model);
		
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
				
			case SEARCH:
				controller = new BookSearchController(m_model);
				return controller;
			
			case SELL:
				controller = new SellViewController(m_model);
				return controller;
				
			case LOGIN:
				controller = new LoginViewController(m_model);
				return controller;
				
			case CONFIG:
				controller = new ConfigViewController(m_model);
				return controller;
				
			case LOGS:
				controller = new LogViewController(m_model);
				return controller;
			
			default:
				return controller;
		}
		
	}
}
