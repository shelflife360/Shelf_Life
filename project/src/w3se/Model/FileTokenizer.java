package w3se.Model;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class FileTokenizer
{
	private String m_readFile = null;
	
	public FileTokenizer()
	{
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
	
	public LinkedList<String> tokenize()
	{
		StringTokenizer tokenizer = new StringTokenizer(m_readFile, ",");
		LinkedList<String> list = new LinkedList<String>();
		
		while (tokenizer.hasMoreTokens())
		{
			list.add(tokenizer.nextToken());
		}
		
		return list;
	}
	
}
