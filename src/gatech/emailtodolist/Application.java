package gatech.emailtodolist;

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

@SuppressWarnings("serial")
public class Application extends JFrame{
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private Settings settings;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
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
	
	private void saveSettings() throws IOException, Exception
	{
		String email = txtEmail.getText();
		char[] password = txtPassword.getPassword();
		
		if(email.length() == 0) throw new Exception("Nothing inputed for email address!");
		if(password.length < 1) throw new Exception("Nothing inputed for password!");
		
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
