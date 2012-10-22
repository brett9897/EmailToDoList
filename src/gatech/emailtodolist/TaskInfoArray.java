package gatech.emailtodolist;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class TaskInfoArray implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2508803737746887433L;
	private ArrayList<TaskInfo> taskInfos = new ArrayList<TaskInfo>();
	
	public void addEntry(TaskInfo ti)
	{
		this.taskInfos.add(ti);
	}
	
	public ArrayList<TaskInfo> getTaskInfos()
	{
		return this.taskInfos;
	}
	
	
	public void save() throws IOException
	{
		FileOutputStream fout = new FileOutputStream("taskInfo.dat");
	    ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(this);
	    oos.close();
	}
	public ArrayList<TaskInfo> load() throws IOException, ClassNotFoundException
	{
		FileInputStream fin = new FileInputStream("taskInfo.dat");
	    ObjectInputStream ois = new ObjectInputStream(fin);
	    TaskInfoArray tia = (TaskInfoArray) ois.readObject();
	    ois.close();
	    this.taskInfos = tia.getTaskInfos();
	    return this.taskInfos;
	}
}
