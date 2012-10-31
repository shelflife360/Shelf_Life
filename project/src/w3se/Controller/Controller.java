package w3se.Controller;


public interface Controller
{
	public ListenerAdaptor getListener(String key);
	
	public void registerView(Object view);
}
