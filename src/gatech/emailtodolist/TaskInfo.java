package gatech.emailtodolist;

import java.io.Serializable;

public class TaskInfo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7710340810238100543L;
	private ToDoList tdl;
	private boolean done;
	private boolean workingOn;
	private boolean needHelp;
	
	public TaskInfo(ToDoList tdl)
	{
		this.tdl = tdl;
	}
	
	public ToDoList getToDoList()
	{
		return tdl;
	}
	
	public boolean isDone()
	{
		return done;
	}
	
	public void isDone(boolean done)
	{
		this.done = done;
	}
	
	public boolean isBeingWorkedOn()
	{
		return workingOn;
	}
	
	public void isBeingWorkedOn(boolean workingOn)
	{
		this.workingOn = workingOn;
	}
	
	public boolean needsHelp()
	{
		return needHelp;
	}
	
	public void needsHelp(boolean needHelp)
	{
		this.needHelp = needHelp;
	}
}
