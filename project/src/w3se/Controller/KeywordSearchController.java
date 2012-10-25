package w3se.Controller;

import java.awt.event.ActionEvent;
import w3se.Base.ListenerAdaptor;
import w3se.Model.IMS;
import w3se.View.Panels.KeywordSearchPanel;

public class KeywordSearchController extends AbstractController
{

	public KeywordSearchController(IMS model)
	{
		
	}
	
	protected void propagateMap()
	{
		addListener(KeywordSearchPanel.SEARCH_FIELD, new
				ListenerAdaptor()
				{
					public void actionPerformed(ActionEvent e)
					{
						System.out.println("hoodie");
					}
				});
		
	}


	@Override
	public void registerView(Object view)
	{
		// TODO Auto-generated method stub
		
	}


}
