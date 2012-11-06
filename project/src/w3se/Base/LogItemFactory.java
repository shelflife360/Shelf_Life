package w3se.Base;

import java.util.UUID;


public class LogItemFactory
{
	public static LogItem createLogItem(String time, int action, String desc)
	{
		LogItem item = new LogItem();
		
		item.setID((int)UUID.randomUUID().getMostSignificantBits());
		item.setAction(action);
		item.setTimeStamp(time);
		item.setDesc(desc);
		
		return item;
	}
}
