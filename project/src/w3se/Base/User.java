package w3se.Base;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class User
{
	public static final int GENERAL = 0;
	public static final int WORKER = 1;
	public static final int MANAGER = 2;
	
	private int m_uid = 0;
	private int m_privilege = GENERAL;
	private String m_name = null;
	private String m_password = null;
	
	public User()
	{}
	
	public User(int id, int userType)
	{
		m_uid = id;
		m_privilege = userType;
	}
	
	public User(int id, int userType, String uName, String pass)
	{
		m_uid = id;
		m_privilege = userType;
		m_name = uName;
		m_password = hashPassword(pass);
	}
	
	public int getUID()
	{
		return m_uid;
	}
	
	public void setUID(int id)
	{
		m_uid = id;
	}
	
	public int getPrivilege()
	{
		return m_privilege;
	}
	
	public void setPrivilege(int type)
	{
		m_privilege = type;
	}
	
	public String getUsername()
	{
		return m_name;
	}
	
	public void setUsername(String uName)
	{
		m_name = uName;
	}
	
	public String getPassword()
	{
		return m_password;
	}
	
	public void setPassword(String password)
	{
		m_password = hashPassword(password);
	}
	
	public void setHashedPassword(String password)
	{
		m_password = password;
	}
	private String hashPassword(String pass)
	{
		String hash = null;
		
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(pass.getBytes());
			
			byte hashData[] = md.digest();
			
			Formatter formatter = new Formatter();
			
			for (int i = 0; i < hashData.length; i++)
			{
				formatter.format("%02x", hashData[i]);
			}
			
			hash = formatter.toString();
		} catch (NoSuchAlgorithmException e)
		{
			System.out.println("Password hashing failed");
		}
		
		return hash;
		
	}
}
