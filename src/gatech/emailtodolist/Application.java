import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

@SuppressWarnings("serial")
public class Application extends JFrame{
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private static Settings settings;
	static ArrayList<ToDoList> toDoListArray = new ArrayList<ToDoList>();

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		new Application();
	}
	
	public Application()
	{
		setTitle("E-ToDo");
		this.setSize(720, 470);
		getContentPane().setLayout(null);
		
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 684, 409);
		getContentPane().add(tabbedPane);
		
		final JPanel pnlLists = new JPanel();
		tabbedPane.addTab("Lists", null, pnlLists, null);
		pnlLists.setLayout(new FormLayout(new ColumnSpec[] {},
			new RowSpec[] {}));
		
		JPanel pnlSettings = new JPanel();
		tabbedPane.addTab("Settings", null, pnlSettings, null);
		pnlSettings.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblEmail = new JLabel("Email Address:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 24));
		pnlSettings.add(lblEmail, "4, 4");
		
		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 24));
		pnlSettings.add(txtEmail, "8, 4, fill, default");
		txtEmail.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 24));
		pnlSettings.add(lblPassword, "4, 8, left, bottom");
		
		txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 24));
		pnlSettings.add(txtPassword, "8, 8, fill, default");
		
		//attempt to load settings
		if( !loadSettings() ) tabbedPane.setSelectedComponent(pnlSettings);
		
		JButton btnSaveSettings = new JButton("Save Settings");
		btnSaveSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					saveSettings();
					grabEmails();
					tabbedPane.setSelectedComponent(pnlLists);					
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(getContentPane(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSaveSettings.setFont(new Font("Tahoma", Font.PLAIN, 24));		
		pnlSettings.add(btnSaveSettings, "4, 12");
		setVisible(true);
	}
	
	private void grabEmails() throws Exception{
		String host = "imap.gmail.com";
		String user = settings.email();
		String password = settings.plainTextPassword(); 

		// Get system properties 
		Properties properties = System.getProperties(); 

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		// Get a Store object that implements the specified protocol.
		Store store = session.getStore("imaps");

		//Connect to the current host using the specified username and password.
		store.connect(host, user, password);

		//Create a Folder object corresponding to the given name.
		Folder folder = store.getFolder("inbox");

		// Open the Folder.
		folder.open(Folder.READ_ONLY);

		Message[] messages = folder.getMessages();

		// Display message.
		for (int i = messages.length - 1; i > 0; i--) {
			
			String[] subjectContents;
			
			if (messages[i].getSubject() != null){
				subjectContents = messages[i].getSubject().split(" ");
				
				if (subjectContents[0].equals("ToDo:")){
					
					String date = "" + messages[i].getSentDate();
					String from = "" + messages[i].getFrom()[0];
					String subject = "" + messages[i].getSubject();
					String contents = "" + ((MimeMessage)messages[i]).getContent();
					
					ToDoList toDoList = new ToDoList(date, from, subject);
					
					String[] messageContents = contents.split("\n");
					
					for (int j = 0; j <= messageContents.length - 1; j++){
						toDoList.toDoItems.add(messageContents[j]);
					}
					
					toDoListArray.add(toDoList);					
					
//					System.out.println("------------ Message " + (i + 1) + " ------------");
//					System.out.println("SentDate : " + messages[i].getSentDate());
//					System.out.println("From : " + messages[i].getFrom()[0]);
//					System.out.println("Subject : " + messages[i].getSubject());
//					System.out.print("Message : \n" + ((MimeMessage)messages[i]).getContent());
//		
//					System.out.println();
				}
			}						
		}

		folder.close(true);
		store.close();
	}
	
	private void saveSettings() throws IOException, Exception
	{
		String email = txtEmail.getText();
		char[] password = txtPassword.getPassword();
		
		if(email.length() == 0) throw new Exception("Please input an email address");
		if(password.length < 1) throw new Exception("Please input a password");
		
		//TODO RegEx matching on email
		
		if(this.settings == null)
		{
			this.settings = new Settings(email, password);
		}
		else
		{
			this.settings.email(txtEmail.getText());
			this.settings.password(txtPassword.getPassword());
		}
		
		this.settings.save();
		this.loadSettings();
	}
	
	private boolean loadSettings()
	{
		try
		{
			settings = new Settings();
			txtEmail.setText(settings.email());
			txtPassword.setText(settings.password());
			return true;
		}
		catch(Exception e)
		{
			txtEmail.setText("");
			txtPassword.setText("");
			return false;
		}
	}
}
