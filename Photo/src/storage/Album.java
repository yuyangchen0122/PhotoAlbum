package storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @authot Yuyang Chen  168008482
 * @author JAMES DEHART jrd250
 */
public class Album implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public ArrayList<Photo> photos;
	
	public String name;
	/* Create a completely new album */

    /**
     * album
     * @param n
     */
    public Album(String n)
	{
		name = n;
		photos = new ArrayList<Photo>();
	}
	/* Copy searched album over */

    /**
     * album
     * @param n
     * @param col
     */
    public Album(String n, Collection<Photo> col)
	{
		this(n);
		for(Photo p : col)
		{
			photos.add(new Photo(p));
		}
	}
	
}
