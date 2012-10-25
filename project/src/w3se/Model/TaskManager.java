package w3se.Model;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

import w3se.Base.User;

public class TaskManager extends Observable
{
	private Queue<Task> m_taskQueue = new LinkedList<Task>();
	
	public void addTask(Task task)
	{
		m_taskQueue.add(task);
		setChanged();
		notifyObservers("task_added");
	}
	
	public void runTask()
	{
		User currentUser = IMS.getInstance().getUser();
		int privilege = currentUser.getPrivilege();
		Task task = m_taskQueue.remove();
		
		if (task.getPrivilege() <= privilege)
		{
			task.run();
		}
		
		
	}
	
	public boolean hasTask()
	{
		if (m_taskQueue.peek() != null)
			return true;
		else
			return false;
	}
	
	
}
