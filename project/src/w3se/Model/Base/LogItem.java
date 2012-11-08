package w3se.Model.Base;

import java.sql.Timestamp;

import w3se.Model.Exportable;

public class LogItem implements Exportable
{
	public static final int INVENTORY = 0;
	public static final int USER = 1;
	public static final int SYSTEM = 2;
	public static final int LOGIN = 3;
	public static final int SALES = 4;
	public static final int ALL = -1;
	
	private long m_id = 0;
	private Timestamp m_timeStamp;
	private int m_action = 0;
	private String m_desc = "";
	
	public LogItem()
	{}
	
	public LogItem(long id, Timestamp timeStamp, int action, String desc)
	{
		m_id = id;
		m_timeStamp = timeStamp;
		m_action = action;
		m_desc = desc;
	}

	public long getID()
	{
		return m_id;
	}

	public void setID(long id)
	{
		m_id = id;
	}

	public Timestamp getTimeStamp()
	{
		return m_timeStamp;
	}
	
	public String getTimeStampString()
	{
		return m_timeStamp.toString();
	}
	public void setTimeStamp(Timestamp timeStamp)
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
	
	public String getExportableTitle()
	{
		return "Log Entry Database Export";
	}
	
	public String[] getExportableHeadRow()
	{
		String[] format = new String[4];
		format[0] = "L_ID";
		format[1] = "Timestamp";
		format[2] = "Action";
		format[3] = "Description";
		
		return format;
	}
	
	public String[] getExportableRow()
	{
		String[] format = new String[4];
		format[0] = ""+m_id;
		format[1] = getTimeStampString();
		format[2] = ""+m_action;
		format[3] = m_desc;
		
		return format;
	}
}
