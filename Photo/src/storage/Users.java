package storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * @authot Yuyang Chen  168008482
 * @author JAMES DEHART jrd250
 */
public class Users implements Serializable {


	private static final long serialVersionUID = 1L;
	
	public static final String storeDir = "PhotoData53";
	public static final String storeFile = "users.dat";
	
	public HashMap<String, User> users;

	public Users()
	{
		users = new HashMap<String, User>();
	}

    /**
     *
     * @param u
     * @throws IOException
     * @throws URISyntaxException
     */
    public static void writeUsers(Users u) throws IOException, URISyntaxException
	{
		ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(new File(URI.class.getResource("/data/users.dat").toURI())));
		stream.writeObject(u);
		stream.close();
	}

    /**
     * read users
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws URISyntaxException
     */
    public static Users readUsers() throws IOException, ClassNotFoundException, URISyntaxException
	{
		ObjectInputStream stream = new ObjectInputStream(new FileInputStream(new File(URI.class.getResource("/data/users.dat").toURI())));
		Users u = (Users) stream.readObject();
		stream.close();
		return u;
	}
}
