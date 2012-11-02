package w3se.Model;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class FileTokenizer
{
	private String m_readFile = null;
	public static final int UPPER_CASE = 0;
	public static final int LOWER_CASE = 1;
	public static final int UNTOUCHED = 2;
	private int m_tokenType = UPPER_CASE;
	
	public FileTokenizer(int tokenType)
	{
		m_tokenType = tokenType;
	}
	
	public void readFile(String filename)
	{
		try
		{	
			BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(filename));
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			
			int result = 0;
			
			while ((result = inStream.read()) > -1)
			{
				byteStream.write(result);
			}
			
			inStream.close();
			
			m_readFile = byteStream.toString();
			
		} 
		catch (Exception e)
		{
			System.out.println("File not found!");
		} 
	}
	
	public ArrayList<String> tokenize()
	{
		StringTokenizer tokenizer = new StringTokenizer(m_readFile, ", ");
		ArrayList<String> list = new ArrayList<String>();
		
		while (tokenizer.hasMoreTokens())
		{
			String token = tokenizer.nextToken().replace(" ", "");
			if (m_tokenType == UPPER_CASE)
				list.add(token.toUpperCase());
			else if (m_tokenType == LOWER_CASE)
				list.add(token.toLowerCase());
			else
				list.add(token);
		}
		
		return list;
	}
	
}
