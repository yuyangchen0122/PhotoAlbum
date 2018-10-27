package storage;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @authot Yuyang Chen  168008482
 * @author JAMES DEHART jrd250
 */
public class User implements Serializable {


	private static final long serialVersionUID = 1L;

	public String userName;
	
	public HashMap<String, Album> albums;

    /**
     * user
     * @param u
     */
    public User(String u)
	{
		userName = u;
		albums = new HashMap<String, Album>();
	}

    /**
     * handle move photo
     * @param a
     * @param b
     * @param p
     */
    public void movePhotoTo(String a, String b, Photo p)
	{
		albums.get(a).photos.remove(p);
		albums.get(b).photos.add(p);
	}

    /**
     * hande rename album
     * @param a
     * @param newName
     */
    public void renameAlbum(String a, String newName)
	{
		/* generate error */
		if(!albums.containsKey(a))
		{
			return;
		}

		Album tmp = albums.remove(a);
		tmp.name = newName;
		albums.put(newName, tmp);
	}
	
}
