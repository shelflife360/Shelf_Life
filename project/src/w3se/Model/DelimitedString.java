package w3se.Model;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class DelimitedString
{
	private String m_string = null;
	private String m_delimiter = null;
	
	public DelimitedString(String str, String delimiter)
	{
		m_string = str;
		m_delimiter = delimiter;
	}
	
	public int getLength()
	{
		return m_string.length();
	}
	
	public void replace(String original, String replacement)
	{
		m_string.replace(original, replacement);
	}
	
	public ArrayList<String> getDelimitedStrings()
	{
		StringTokenizer strTokenizer = new StringTokenizer(m_string, m_delimiter);
		ArrayList<String> list = new ArrayList<String>();
		
		while(strTokenizer.hasMoreTokens())
		{
			list.add(strTokenizer.nextToken());
		}
		
		return list;
	}
}
