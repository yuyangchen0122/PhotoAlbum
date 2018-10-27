package view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import storage.Photo;
import storage.Users;

/**
 * @authot Yuyang Chen  168008482
 * @author JAMES DEHART jrd250
 */
public class PaneSelector {
	
	public static Stage primaryStage;
	public static Users  users;
	
	private static Scene oldLoginScene;
	private static Scene oldCreateNewUserScene;
	private static Scene oldGalleryScene;
	private static Scene oldAlbumSelectScene;
	private static Scene oldDisplayScene;
	private static Scene oldSearchGalleryScene;

    /**
     * set new log in
     * @throws IOException
     */
    public static void setNewLogin() throws IOException
	{
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				URL.class.getResource("/view/Login.fxml"));
		
		AnchorPane root = (AnchorPane)loader.load();


		LoginController loginController = loader.getController();
		loginController.start(users, primaryStage);

		Scene scene = new Scene(root);
		
		oldLoginScene = scene;
		
		primaryStage.setScene(scene);
		
		primaryStage.show(); 
	}

    /**
     * set Old Login
     */
    public static void setOldLogin()
	{
		if(oldLoginScene == null)
			return;
		primaryStage.setScene(oldLoginScene);
	}

    /**
     * set New Create New User
     */
    public static void setNewCreateNewUser()
	{
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				URL.class.getResource("/view/CreateNewUser.fxml"));
		
		AnchorPane root;
		
		try {
			root = (AnchorPane)loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return;
		}


		CreateNewUserController controller = loader.getController();

		controller.start(users, primaryStage);

		Scene scene = new Scene(root);
		
		oldCreateNewUserScene = scene;
		
		primaryStage.setScene(scene);
		
		primaryStage.show(); 
	}

    /**
     * set Old Create New User
     */
    public static void setOldCreateNewUser()
	{
		if(oldCreateNewUserScene == null)
			return;
		primaryStage.setScene(oldCreateNewUserScene);
	}

    /**
     * setNewGallery
     * @param userName
     * @param album
     */
    public static void setNewGallery(String userName, String album)
	{
		
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				URL.class.getResource("/view/Gallery.fxml"));

		BorderPane root;
		
		try {
			root = (BorderPane)loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return;
		}


		GalleryController controller = loader.getController();

		controller.start(users, primaryStage, userName, album);

		Scene scene = new Scene(root);
		
		oldGalleryScene = scene;
		
		primaryStage.setScene(scene);
		
		primaryStage.show(); 
	}

    /**
     * setOldGallery
     */
    public static void setOldGallery()
	{
		if(oldGalleryScene == null)
			return;
		primaryStage.setScene(oldGalleryScene);
	}

    /**
     * setNewAlbumSelect
     * @param user
     */
    public static void setNewAlbumSelect(String user)
	{
		
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				URL.class.getResource("/view/AlbumSelect.fxml"));
		
		BorderPane root;
		
		try {
			root = (BorderPane)loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return;
		}


		AlbumSelectController controller = loader.getController();

		controller.start(users, primaryStage, user);

		Scene scene = new Scene(root);
		
		oldAlbumSelectScene = scene;
		
		primaryStage.setScene(scene);
		
		primaryStage.show(); 
	}

    /**
     * setOldAlbumSelect
     */
    public static void setOldAlbumSelect()
	{
		if(oldAlbumSelectScene == null)
			return;
		primaryStage.setScene(oldAlbumSelectScene);
	}

    /**
     * setNewDisplay
     * @param user
     * @param album
     * @param index
     */
    public static void setNewDisplay(String user, String album, int index)
	{
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				URL.class.getResource("/view/Display.fxml"));
		
		BorderPane root;
		
		try {
			root = (BorderPane)loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return;
		}


		DisplayController controller = loader.getController();

		controller.start(users, primaryStage, user, album, index);

		Scene scene = new Scene(root);
		
		oldDisplayScene = scene;
		
		primaryStage.setScene(scene);
		
		primaryStage.show(); 
	}
	
	public static void setOldDisplay()
	{
		if(oldDisplayScene == null)
			return;
		primaryStage.setScene(oldDisplayScene);
	}

    /**
     * setNewSearchGallery
     * @param p
     * @param user
     */
    public static void setNewSearchGallery(ArrayList<Photo> p, String user)
	{
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				URL.class.getResource("/view/SearchGallery.fxml"));
		
		BorderPane root;
		
		try {
			root = (BorderPane)loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return;
		}


		SearchGalleryController controller = loader.getController();

		controller.start(users, user, primaryStage, p);

		Scene scene = new Scene(root);
		
		oldSearchGalleryScene = scene;
		
		primaryStage.setScene(scene);
		
		primaryStage.show(); 
	}

    /**
     * setOldSearchGallery
     */
    public static void setOldSearchGallery()
	{
		if(oldSearchGalleryScene == null)
			return;
		primaryStage.setScene(oldSearchGalleryScene);
	}

    /**
     * setNewAdmin
     */
    public static void setNewAdmin()
	{
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				URL.class.getResource("/view/Admin.fxml"));
		
		BorderPane root;
		
		try {
			root = (BorderPane)loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return;
		}


		AdminController controller = loader.getController();

		controller.start(users, primaryStage);

		Scene scene = new Scene(root);
		
		//oldSearchGalleryScene = scene;
		
		primaryStage.setScene(scene);
		
		primaryStage.show(); 
	}
}
