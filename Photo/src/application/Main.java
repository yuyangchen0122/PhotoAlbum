package application;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;
import storage.Album;
import storage.Photo;
import storage.User;
import storage.Users;
import view.PaneSelector;

/**
 * @authot Yuyang Chen  168008482
 * @author JAMES DEHART jrd250
 */
public class Main extends Application {
    Users users;

    @Override
    public void start(Stage primaryStage) throws IOException{

        primaryStage.setResizable(false);
        /* First load the old users object if it is there */
        try
        {
            users = Users.readUsers();
        }
        catch(Exception ex)
        {
            users = new Users();
        }

        setUpStock();

        PaneSelector.users = users;
        PaneSelector.primaryStage = primaryStage;

        PaneSelector.setNewLogin();

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws URISyntaxException
    {
        try {
            Users.writeUsers(users);
        } catch (IOException e) {
        }
    }
    /* Really clunky */
    public void setUpStock()
    {
        if(!users.users.containsKey("stock"))
            users.users.put("stock", new User("stock"));

        ArrayList<Photo> files = new ArrayList<Photo>();
        String[] locations = {"/data/asmon.png", "/data/crono.jpg", "/data/fatcat.jpg", "/data/fish.jpeg", "/data/jinko.jpg", "/data/meme.jpg", "/data/plasticcup.jpg", "/data/vangogh.jpg", "/data/waldo.jpg" };
        File tmp;

        if(!users.users.get("stock").albums.containsKey("stock"))
            users.users.get("stock").albums.put("stock", new Album("stock"));
        Album stockAlbum = users.users.get("stock").albums.get("stock");
        boolean alreadyHas = false;

        for(String i : locations)
        {
            try
            {
                URI uri = getClass().getResource(i).toURI();
                tmp = new File(getClass().getResource(i).toURI());

                if(!tmp.exists())
                {
                    continue;
                }

                for(Photo j : stockAlbum.photos)
                {
                    if(uri.equals(j.location))
                    {
                        alreadyHas = true;
                    }
                }
                if(!alreadyHas)
                    files.add(new Photo(uri));
                alreadyHas = false;
            }
            catch(Exception ex)
            {
                alreadyHas = false;
            }
        }


        for(Photo i : files)
        {
            users.users.get("stock").albums.get("stock").photos.add(i);
        }

    }
}
