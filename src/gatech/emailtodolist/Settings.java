package gatech.emailtodolist;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Settings implements Serializable
{
	private static final long serialVersionUID = 7264812831834523030L;
	private static final String SEED = "90210H@sh9897";
	private String email;
	private String password;
	public boolean emailGiven = false;
	public boolean passwordGiven = false;
	
	//ClassNotFoundException should never occur
	public Settings() throws IOException, ClassNotFoundException
	{
		this.load();
	}
	
	public Settings(String email, char[] password) throws Exception
	{
		this.email = email;
		this.password = SimpleCrypto.encrypt(SEED, new String(password));
		emailGiven = true;
		passwordGiven = true;
	}
	
	public void email(String email)
	{
		this.email = email;
	}
	
	public String email()
	{
		return this.email;
	}
	
	public void password(char[] password) throws Exception
	{
		String strPass = new String(password);
		if( strPass.compareTo(this.password) != 0 )
			this.password = SimpleCrypto.encrypt(SEED, strPass);
	}
	
	public String password()
	{
		return this.password;
	}
	
	public String plainTextPassword() throws Exception
	{
		return SimpleCrypto.decrypt(SEED, this.password);
	}
	
	public void save() throws IOException
	{
		FileOutputStream fout = new FileOutputStream("settings.dat");
	    ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(this);
	    oos.close();
	}
	
	public void load() throws IOException, ClassNotFoundException
	{
		FileInputStream fin = new FileInputStream("settings.dat");
	    ObjectInputStream ois = new ObjectInputStream(fin);
	    Settings s = (Settings) ois.readObject();
	    ois.close();
	    this.email = s.email();
	    this.password = s.password();
	    s = null; //cleanup
	}
}
