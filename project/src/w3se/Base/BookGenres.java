package w3se.Base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import w3se.Model.FileTokenizer;

public class BookGenres
{
	private HashMap<String, String> m_genreList = null;
	private String m_filename = null;
	public BookGenres()
	{
		m_genreList = new HashMap<String, String>();
	}
	
	public void mergeFromFile(String filename)
	{
		m_filename = filename;
		FileTokenizer tokenizer = new FileTokenizer(FileTokenizer.UPPER_CASE);
		tokenizer.readFile(m_filename);
		ArrayList<String> tokens = new ArrayList<String>();
		tokens = tokenizer.tokenize();
		for (int i = 0; i < tokens.size(); i++)
		{
			if (!m_genreList.containsKey(tokens.get(i)))
			{
				m_genreList.put(tokens.get(i), tokens.get(i));
			}
		}
	}
	
	public void mergeFromBook(Book book)
	{
		String str = book.getGenres();
		StringTokenizer tokenizer = new StringTokenizer(",");
		ArrayList<String> tokens = new ArrayList<String>();
		
		while (tokenizer.hasMoreTokens())
		{
			String tok = tokenizer.nextToken().toUpperCase();
			if (!m_genreList.containsKey(tok))
			{
				m_genreList.put(tok, tok);
			}
		}
		
	}
	
	public void addGenre(String genre)
	{
		m_genreList.put(genre, genre);
	}
	
	public String getGenre(String genre)
	{
		return m_genreList.get(genre);
	}
	
	public void removeGenre(String genre)
	{
		m_genreList.remove(genre);
	}
	
	public int getSize()
	{
		return m_genreList.size();
	}
	
	public String[] toStringArray()
	{
		String[] genres = new String[m_genreList.size()];
		Iterator<Entry<String, String>> iter = m_genreList.entrySet().iterator();
		
		int i = 0;
		while (iter.hasNext())
		{
			Map.Entry<String,String> pair = iter.next();
			genres[i] = pair.getValue();
			i++;
		}
		return genres;
	}
}
