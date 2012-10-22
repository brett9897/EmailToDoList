package gatech.emailtodolist;

import java.io.Serializable;
import java.util.ArrayList;

public class ToDoList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4665317704116269153L;
	public String sender;
	public String receivedOn;
	public String title;
	public ArrayList<String> toDoItems = new ArrayList<String>();
	
	public ToDoList(String senderInfo, String dateReceived, String listTitle){
		sender = senderInfo;
		receivedOn = dateReceived;
		title = listTitle;
	}
	
	public boolean equals(ToDoList t)
	{
		if( (this.sender.compareTo(t.sender) == 0) && (this.receivedOn.compareTo(t.receivedOn) == 0) 
				&& (this.title.compareTo(t.title) == 0))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
