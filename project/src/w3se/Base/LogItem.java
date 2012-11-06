package w3se.Base;

public class LogItem
{
	public static final int INVENTORY = 0;
	public static final int USER = 1;
	public static final int SYSTEM = 2;
	public static final int LOGIN = 3;
	public static final int SALES = 4;
	public static final int ALL = -1;
	
	private int m_id = 0;
	private String m_timeStamp = "";
	private int m_action = 0;
	private String m_desc = "";
	
	public LogItem()
	{}
	
	public LogItem(int id, String timeStamp, int action, String desc)
	{
		m_id = id;
		m_timeStamp = timeStamp;
		m_action = action;
		m_desc = desc;
	}

	public int getID()
	{
		return m_id;
	}

	public void setID(int id)
	{
		m_id = id;
	}

	public String getTimeStamp()
	{
		return m_timeStamp;
	}

	public void setTimeStamp(String timeStamp)
	{
		m_timeStamp = timeStamp;
	}

	public int getAction()
	{
		return m_action;
	}

	public void setAction(int action)
	{
		m_action = action;
	}

	public String getDesc()
	{
		return m_desc;
	}

	public void setDesc(String desc)
	{
		m_desc = desc;
	}
	
	
}
