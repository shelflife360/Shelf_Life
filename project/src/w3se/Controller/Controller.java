package w3se.Controller;

import w3se.Base.ListenerAdaptor;

public interface Controller
{
	public ListenerAdaptor getListener(String key);
	
	public void registerView(Object view);
}
