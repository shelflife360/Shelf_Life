package w3se.Base;

import java.util.LinkedList;

public class BookGenres
{
	private LinkedList<String> m_genreList = null;
	
	public BookGenres()
	{
		m_genreList = new LinkedList<String>();
	}
	
	public void addGenre(String genre)
	{
		m_genreList.add(genre);
	}
	
	public String getGenre(int index)
	{
		return m_genreList.get(index);
	}
	
	public void removeGenre(int index)
	{
		m_genreList.remove(index);
	}
	
	public String[] toStringArray()
	{
		String[] genres = new String[m_genreList.size()];
		
		for (int i = 0; i < m_genreList.size(); i++)
		{
			genres[i] = m_genreList.get(i);
		}
		
		return genres;
	}
}
