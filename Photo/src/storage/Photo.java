package storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.net.URI;
import java.util.Calendar;
import java.util.HashMap;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @authot Yuyang Chen  168008482
 * @author JAMES DEHART jrd250
 */
public class Photo implements Serializable {


	private static final long serialVersionUID = 1L;

	public URI location;
	
	public String caption;
	
	public HashMap<String, String> tags;

	public static String[] tagTypes = { "person1", "person2", "person3", "color", "location", "mood", "type"};

    /**
     * photo
     * @param loc
     */
    public Photo(URI loc)
	{
		/* IMMUTABLE SHOULD SOLVE */
		location = loc;
		caption = "";
		tags = new HashMap<String, String>();
	}
	/* copy constructor */

    /**
     * photo
     * @param orig
     */
    public Photo(Photo orig)
	{
		this(orig.location);
		this.caption = orig.caption;
		/* pretty sure strings are immutable so auto deep copy */
		/* MAY NEED TO CHANGE REMEMBER THIS */
		for(String key : orig.tags.keySet())
		{
			this.tags.put(key, orig.tags.get(key));
		}
	}

    /**
     * get Last Modified Date
     * @return
     */
    public String getLastModifiedDate()
	{
		try
		{
			File f = new File(location);
			
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(f.lastModified());
			c.set(Calendar.MILLISECOND,0);
			
			
			return c.getTime().toString();
		}
		catch(Exception e)
		{
			Calendar c = Calendar.getInstance();
			return c.getTime().toString();
		}
	}

    /**
     * handle get DATE
     * @return
     */
    private Calendar getDate()
	{
		try
		{
			File f = new File(location);
			
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(f.lastModified());
			c.set(Calendar.MILLISECOND,0);
			
			
			return c;
		}
		catch(Exception e)
		{
			Calendar c = Calendar.getInstance();
			return c;
		}
	}

    /**
     * handle above date
     * @param year1
     * @param month1
     * @param day1
     * @return
     */
    public boolean aboveDate(int year1, int month1, int day1)
	{
		Calendar c = getDate();
		
		int year2 = c.get(Calendar.YEAR);
		int month2 = c.get(Calendar.MONTH) + 1;
		int day2 = c.get(Calendar.DAY_OF_MONTH);
		
		if( (year1 > year2) || ((year1 == year2) && (month1 > month2)) || ((year1 == year2) && (month1 == month2) && (day1 > day2)))
			return false;
		return true;
		
	}

    /**
     * handle below date
     * @param year2
     * @param month2
     * @param day2
     * @return
     */
    public boolean belowDate(int year2, int month2, int day2)
	{
		Calendar c = getDate();
		
		int year1 = c.get(Calendar.YEAR);
		int month1 = c.get(Calendar.MONTH) + 1;
		int day1 = c.get(Calendar.DAY_OF_MONTH);
		
		if( (year1 > year2) || ((year1 == year2) && (month1 > month2)) || ((year1 == year2) && (month1 == month2) && (day1 > day2)))
			return false;
		return true;
	}

    /**
     * get image view
     * @return
     */
    public ImageView getImageView()
	{
		Image image;
		
		try
		{
			image = new Image(new FileInputStream(new File(location)));
			
		}
		catch(Exception e)
		{
			return null;
		}
		
		
		ImageView iView = new ImageView(image);
		dimensionalize(iView, image);
		
		return iView;
	}

    /**
     * get slide show view
     * @return
     */
    public ImageView getSlideShowView()
	{
		Image image;
		
		try
		{
			image = new Image(new FileInputStream(new File(location)));
			
		}
		catch(Exception e)
		{

			return null;
		}
		
		/* Max dimensions are width = 670(684) and 570 */
		ImageView iView = new ImageView(image);
		
		iView.setPreserveRatio(true);
		

		if(image.getWidth() > 684 && image.getHeight() > 570)
		{
			double coW = image.getWidth() / 684;
			
			double coH = image.getHeight() / 570;
			if(coW >= coH)
			{
				iView.setFitWidth(684);
				double margin = (570 - (image.getHeight() / coW)) / 2.0;
				iView.setTranslateY(margin);
			}
			else
			{
				iView.setFitHeight(570);
				double margin = (684 - (image.getWidth() / coH)) / 2.0;
				iView.setTranslateX(margin);
				
			}
		}
		else if(image.getWidth() > 684)
		{
			iView.setFitWidth(684);
			double co = image.getWidth() / 684;
			double margin = (570 - (image.getHeight() / co)) / 2.0;
			iView.setTranslateY(margin);
		}
		else if(image.getHeight() > 570)
		{
			iView.setFitHeight(570);
			double co = image.getHeight() / 570;
			double margin = (684 - (image.getWidth() / co)) / 2.0;
			iView.setTranslateX(margin);
		}
		else
		{
			double margin1 = (684 - image.getWidth()) / 2;
			double margin2 = (570 - image.getHeight()) / 2;
			
			iView.setTranslateX(margin1);
			iView.setTranslateY(margin2);
		}
		
		
		
		
		
		return iView;
	}

    /**
     * hanle dimensionalize
     * @param iView
     * @param image
     */
    private void dimensionalize(ImageView iView, Image image)
	{
		/* Remember gallery is 800 x 500 and want to fit 4 images per row */
		/* Need to make thumbnail 200 x whatever */
		iView.setPreserveRatio(true);
		if(image.getWidth() > image.getHeight())
		{
			iView.setFitWidth(200);
			
		}
		else
		{
			iView.setFitHeight(200);
		}
		
		
	}
}
