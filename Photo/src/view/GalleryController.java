package view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import storage.Album;
import storage.Photo;
import storage.Users;

/**
 * @authot Yuyang Chen  168008482
 * @author JAMES DEHART jrd250
 */
public class GalleryController {
	
	@FXML
	public BorderPane borderPane;
	
	@FXML
	public ScrollPane scrollPane;
	
	@FXML
	public HBox hbox;
	
	@FXML
	public VBox firstBox;
	
	@FXML
	public VBox secondBox;
	
	@FXML
	public VBox thirdBox;
	
	@FXML
	public StackPane selectedPhotoHolder;
	
	@FXML
	public Button addNewPhoto;
	
	@FXML
	public Button removeSelectedPhoto;
	
	@FXML
	public Button displaySelectedPhoto;
	
	@FXML
	public Button copyToOtherAlbum;
	
	@FXML
	public Button moveToOtherAlbum;
	
	@FXML
	public Button slideShow;
	
	@FXML
	public Button logOut;
	
	@FXML
	public Button selectDifferentAlbum;


	public Users users;
	
	Integer currentRow;
	Integer currentColumn;
	
	StackPane selectedPane;
	VBox      selectedPaneParent;
	int       selectedPaneIndex;
	
	String theUser;
	String theAlbum;

    /**
     * start
     * @param u
     * @param primaryStage
     * @param name
     * @param album
     */
    public void start(Users u, Stage primaryStage, String name, String album)
	{
		users = u;
		theUser = name;
		theAlbum = album;
		/* 600 + 12 + 20 */
		hbox.setSpacing(10);
		firstBox.setSpacing(10);
		secondBox.setSpacing(10);
		thirdBox.setSpacing(10);
		currentRow = 0;
		currentColumn = 0;
		

		ArrayList<Photo> photos = u.users.get(name).albums.get(album).photos;
		for(Photo i : photos)
		{
			addPhoto(i.getImageView(), i.getImageView());
		}

		
		/* Add all the billions of events */
		

		addNewPhoto.setOnMouseClicked((e) ->
		{
			addNewPhoto(primaryStage);
		});
		
		removeSelectedPhoto.setOnMouseClicked((e) ->
		{
			removeSelectedPhoto(primaryStage);
		});

		logOut.setOnMouseClicked((e) ->
		{
			logOut(primaryStage);
		});
		
		selectDifferentAlbum.setOnMouseClicked((e) ->
		{
			selectDifferentAlbum(primaryStage);
		});
		
		copyToOtherAlbum.setOnMouseClicked((e) ->
		{
			copyToOtherAlbum(primaryStage);
		});
		
		moveToOtherAlbum.setOnMouseClicked((e) ->
		{
			moveToOtherAlbum(primaryStage);
		});
		
		displaySelectedPhoto.setOnMouseClicked((e) ->
		{
			displaySelectedPhoto(primaryStage);
		});
		
		slideShow.setOnMouseClicked((e) ->
		{
			slideShow(primaryStage);
		});
	}

    /**
     * add photo
     * @param p
     * @param p2
     */
    public void addPhoto(ImageView p, ImageView p2)
	{
		if(p == null)
			return;
		
		StackPane sp = new StackPane(p);
		sp.setId(currentRow.toString() + "_" + currentColumn.toString());
		sp.setOnMouseClicked((e) -> {
			sp.setStyle("-fx-border-color: blue; -fx-border-width: 2;");
			
			selectedPhotoHolder.getChildren().clear();
			selectedPhotoHolder.getChildren().add(p2);
			if(selectedPane != null)
			{
				selectedPane.setStyle("-fx-border-color: black; -fx-border-width: 2;");
			}
			selectedPane = sp;
		});
		
		sp.setMinWidth(204);
		sp.setMinHeight(204);
		sp.setStyle("-fx-border-color: black; -fx-border-width: 2;");
		StackPane.setAlignment(p, Pos.CENTER);
		
		((VBox) hbox.getChildren().get(currentColumn)).getChildren().add(sp);
		selectedPaneParent = (VBox) hbox.getChildren().get(currentColumn);
		selectedPaneIndex = ((VBox) hbox.getChildren().get(currentColumn)).getChildren().size() - 1;
		
		if(currentColumn == 2)
		{
			currentColumn = 0;
			++currentRow;
		}
		else
			++currentColumn;
		
	}

    /**
     * add new photo
     * @param primaryStage
     */
    public void addNewPhoto(Stage primaryStage)
	{
		FileChooser fileChooser = new FileChooser();
		
		fileChooser.setTitle("Open Image file");
		
		File newFile = fileChooser.showOpenDialog(primaryStage);
		
		if(newFile.exists())
		{
			Photo tmp = new Photo(newFile.toURI());
			users.users.get(theUser).albums.get(theAlbum).photos.add(tmp);
			addPhoto(tmp.getImageView(),tmp.getImageView());
			
		}
	}

    /**
     * remove Selected Photo
     * @param primaryStage
     */
    public void removeSelectedPhoto(Stage primaryStage)
	{
		
		if(selectedPane == null)
			return;
		String tmp = selectedPane.getId();
		String []tmp2 = tmp.split("_");
		int row = Integer.parseInt(tmp2[0]);
		int column = Integer.parseInt(tmp2[1]);
		users.users.get(theUser).albums.get(theAlbum).photos.remove((row * 3) + column);
		PaneSelector.setNewGallery(theUser, theAlbum);
	}

    /**
     * select Different Album
     * @param primaryStage
     */
    public void selectDifferentAlbum(Stage primaryStage)
	{
		PaneSelector.setOldAlbumSelect();
	}

    /**
     * lag out
     * @param primaryStage
     */
    public void logOut(Stage primaryStage)
	{
		PaneSelector.setOldLogin();
	}

    /**
     * copy To Other Album
     * @param primaryStage
     */
    public void copyToOtherAlbum(Stage primaryStage)
	{
		List<String> tmp = new ArrayList<String>();
		
		for(Album a : users.users.get(theUser).albums.values())
		{
			if(!a.name.equals(theAlbum))
				tmp.add(a.name);
		}
		ChoiceDialog<String> dialog;
		if(users.users.get(theUser).albums.size() > 1)
			dialog = new ChoiceDialog<String>(tmp.get(0), tmp);
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("No other album");
			alert.setContentText("There is no other album to copy to");

			alert.showAndWait();
			return;
		}
		dialog.setTitle("Pick Album");
		dialog.setHeaderText("Pick copy to album");
		dialog.setContentText("Choose the album you wish to copy the selected photo to");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		
		if(selectedPane == null)
			return;
		String tmp2 = selectedPane.getId();

		String []tmp3 = tmp2.split("_");
		int row = Integer.parseInt(tmp3[0]);
		int column = Integer.parseInt(tmp3[1]);
		
		if (result.isPresent()){
		    users.users.get(theUser).albums.get(result.get()).photos.add(
		    		new Photo(
		    				users.users.get(theUser).albums.get(theAlbum).photos.get((row * 3) + column)));
		}
	}

    /**
     * move To Other Album
     * @param primaryStage
     */
    public void moveToOtherAlbum(Stage primaryStage)
	{
		copyToOtherAlbum(primaryStage);
		removeSelectedPhoto(primaryStage);
	}

    /**
     * display Selected Photo
     * @param primaryStage
     */
    public void displaySelectedPhoto(Stage primaryStage)
	{
		if(selectedPane == null)
			return;
		String tmp = selectedPane.getId();
		String []tmp2 = tmp.split("_");
		int row = Integer.parseInt(tmp2[0]);
		int column = Integer.parseInt(tmp2[1]);
		PaneSelector.setNewDisplay(theUser, theAlbum, (row * 3) + column);
	}

    /**
     * slide show
     * @param primaryStage
     */
    public void slideShow(Stage primaryStage)
	{
		if(users.users.get(theUser).albums.get(theAlbum).photos.isEmpty())
			return;
		PaneSelector.setNewDisplay(theUser, theAlbum, 0);
	}
	
}
