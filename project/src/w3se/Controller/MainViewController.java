package w3se.Controller;

import java.awt.event.WindowEvent;

import w3se.Base.ListenerAdaptor;
import w3se.Model.IMS;

public class MainViewController extends AbstractController
{
	private IMS m_model = null;
	
	public MainViewController(IMS model)
	{
		m_model = model;
	}
	
	protected void propagateMap()
	{
		addListener("exit", new 
				ListenerAdaptor()
				{
					public void windowClosing(WindowEvent arg0)
					{
						m_model.shutdownSystem();
					}
				});
	}
	
	@Override
	public void registerView(Object view)
	{
		
	}

}
