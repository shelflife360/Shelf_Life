package w3se.Base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import w3se.Model.FileTokenizer;

public class BookGenres
{
	private LinkedHashMap<String, String> m_genreList = null;
	private String m_filename = null;
	public BookGenres()
	{
		m_genreList = new LinkedHashMap<String, String>();
	}
	
	/**
	 * merge genres from a file
	 * @param filename
	 */
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
	
	/**
	 * merge genres from a book
	 * @param book
	 */
	public void mergeFromBook(Book book)
	{
		String str = book.getGenres();
		StringTokenizer tokenizer = new StringTokenizer(str, ", ");
		ArrayList<String> genres = new ArrayList<String>();
		while (tokenizer.hasMoreTokens())
		{
			String tok = tokenizer.nextToken().toUpperCase();
			genres.add(tok);
			if (!m_genreList.containsKey(tok))
			{
				m_genreList.put(tok, tok);
			}
		}
		fixGenres(book, genres);
	}
	
	/**
	 * fix the genre of a book (replace with uppercase versions of what was typed)
	 * @param book - book to have genres altered
	 * @param genres - list of genres
	 */
	private void fixGenres(Book book, ArrayList<String> genres)
	{
		StringBuilder strBuilder = new StringBuilder();
		
		for (int i = 0; i < genres.size(); i++)
		{
			if (i == (genres.size()-1))
				strBuilder.append(genres.get(i));
			else
				strBuilder.append(genres.get(i)+", ");
		}
		
		book.setGenres(strBuilder.toString());
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
	
	public void writeToFile()
	{
		File file = new File(m_filename);
		StringBuilder content = new StringBuilder();
		String[] genres = toStringArray();
		for (int i = 0; i < genres.length; i++)
		{
			content.append(genres[i]+",");
		}
		try
		{
			FileWriter writer = new FileWriter(file.getAbsoluteFile());
			BufferedWriter buffWriter = new BufferedWriter(writer);
			buffWriter.write(content.toString());
			buffWriter.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
