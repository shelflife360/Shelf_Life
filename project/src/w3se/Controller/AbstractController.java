package w3se.Controller;

import java.util.HashMap;



public abstract class AbstractController implements Controller
{
	
	private HashMap<String, ListenerAdaptor> m_listenerMap = new HashMap<String, ListenerAdaptor>();
	
	public ListenerAdaptor getListener(String key)
	{
		return m_listenerMap.get(key);
	}
	
	protected void addListener(String key, ListenerAdaptor listener)
	{
		m_listenerMap.put(key, listener);
	}
	
	abstract protected void propagateMap();
	
}
