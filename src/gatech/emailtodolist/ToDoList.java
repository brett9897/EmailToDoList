import java.util.ArrayList;

public class ToDoList {
	public String sender;
	public String receivedOn;
	public String title;
	public ArrayList<String> toDoItems = new ArrayList<String>();
	
	public ToDoList(String senderInfo, String dateReceived, String listTitle){
		sender = senderInfo;
		receivedOn = dateReceived;
		title = listTitle;
	}
}
