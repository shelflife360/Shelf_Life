package w3se.Model.Base;

import java.util.UUID;



public class LogItemFactory
{
	public static LogItem createLogItem(int action, String desc)
	{
		LogItem item = new LogItem();
		long uuid = UUID.randomUUID().getLeastSignificantBits();
		
		// I hate negative id numbers and Java doesn't have unsigned long so I am just masking the leading bit
		long posMask = ~(1 << 63);
		uuid &= posMask;
		item.setID(uuid);
		item.setAction(action);
		item.setDesc(desc);
		
		return item;
	}
}
