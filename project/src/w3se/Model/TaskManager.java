package w3se.Model;

import java.util.LinkedList;
import java.util.Observable;
import java.util.PriorityQueue;
import java.util.Queue;

import w3se.Base.User;

public class TaskManager extends Observable
{
	private Queue<Task> m_taskQueue = new PriorityQueue<Task>();
	
	/**
	 * method to add a task to the manager
	 * @param task
	 */
	public void addTask(Task task)
	{
		m_taskQueue.offer(task);
		setChanged();
		notifyObservers("task_added");
	}
	
	/**
	 * method to run the next task
	 */
	public void runTask()
	{
		User currentUser = IMS.getInstance().getUser();
		int privilege = currentUser.getPrivilege();
		Task task = m_taskQueue.poll();
		
		if (task.getPrivilege() <= privilege)
		{
			task.run();
		}
		
		
	}
	
	/**
	 * method to test if the manager has any tasks
	 * @return
	 */
	public boolean hasTask()
	{
		if (m_taskQueue.peek() != null)
			return true;
		else
			return false;
	}
	
	
}
