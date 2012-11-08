package w3se.Model.Base;

import java.awt.Color;

public class Theme
{
	private Color m_main;
	private Color m_secondary;
	
	public Theme (Color main, Color secondary)
	{
		m_main = main;
		m_secondary = secondary;
	}
	
	public Color getMainColor()
	{
		return m_main;
	}
	
	public Color getSecondaryColor()
	{
		return m_secondary;
	}
}
