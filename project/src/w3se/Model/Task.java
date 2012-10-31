package w3se.Model;

import w3se.Base.User;

public class Task
{
	private int m_privilege = User.GENERAL;
	private Runnable m_task = null;
	
	public Task(int privilege, Runnable task)
	{
		m_privilege = privilege;
		m_task = task;
	}
	public void setPrivilege(int privilege)
	{
		m_privilege = privilege;
	}
	
	public int getPrivilege()
	{
		return m_privilege;
	}
	
	public void run()
	{
		m_task.run();
	}
}
