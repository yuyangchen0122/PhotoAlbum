package view;

import java.util.ArrayList;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import storage.Album;
import storage.Photo;
import storage.User;
import storage.Users;

/**
 * @authot Yuyang Chen  168008482
 * @author JAMES DEHART jrd250
 */
public class SearchGalleryController {
	
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
	public Button createAlbumWithResults;
	@FXML
	public Button backToAlbums;
	@FXML
	public Button displaySelectedImage;
	@FXML
	public Button logOut;
	
	public Integer currentRow;
	public Integer currentColumn;
	
	public StackPane selectedPane;
	public VBox      selectedPaneParent;
	public int       selectedPaneIndex;
	public Users users;
	public String theUser;
	public ArrayList<Photo> thePhotos;

    /**
     * start
     * @param u
     * @param user
     * @param primaryStage
     * @param p
     */
    public void start(Users u, String user, Stage primaryStage, ArrayList<Photo> p)
	{
		users = u;
		thePhotos = p;
		theUser = user;
		hbox.setSpacing(10);
		firstBox.setSpacing(10);
		secondBox.setSpacing(10);
		thirdBox.setSpacing(10);
		currentRow = 0;
		currentColumn = 0;
		
		for(Photo i : p)
		{
			addPhoto(i.getImageView(), i.getImageView());
		}
		
		logOut.setOnMouseClicked((e) ->
		{
			logOut(primaryStage);
		});
		
		backToAlbums.setOnMouseClicked((e) ->
		{
			backToAlbums(primaryStage);
		});
		
		displaySelectedImage.setOnMouseClicked((e) ->
		{
			displaySelectedImage(primaryStage);
		});
		
		createAlbumWithResults.setOnMouseClicked((e) ->
		{
			createAlbumWithResults(primaryStage);
		});
	}

    /**
     * addPhoto
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
     * logOut
     * @param primaryStage
     */
    public void logOut(Stage primaryStage)
	{
		PaneSelector.setOldLogin();
	}

    /**
     * backToAlbums
     * @param primaryStage
     */
    public void backToAlbums(Stage primaryStage)
	{
		PaneSelector.setOldAlbumSelect();
	}

    /**
     * displaySelectedImage
     * @param primaryStage
     */
    public void displaySelectedImage(Stage primaryStage)
	{
		users.users.put("admin", new User("admin"));
		users.users.get("admin").albums.put("admin", new Album("admin"));
		for(Photo p : thePhotos)
		{
			users.users.get("admin").albums.get("admin").photos.add(p);
		}
		
		if(selectedPane == null)
			return;
		String tmp = selectedPane.getId();
		String []tmp2 = tmp.split("_");
		int row = Integer.parseInt(tmp2[0]);
		int column = Integer.parseInt(tmp2[1]);
		
		PaneSelector.setNewDisplay("admin", "admin", (row * 3) + column);
	}

    /**
     * createAlbumWithResults
     * @param primaryStage
     */
    public void createAlbumWithResults(Stage primaryStage)
	{
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Create Album Results");
		dialog.setHeaderText("Create An Album with search results");
		dialog.setContentText("Enter album name:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    if(users.users.get(theUser).albums.containsKey(result.get()))
		    {
		    	Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Album naming Error");
				alert.setHeaderText("Album naming error");
				alert.setContentText("The album name entered already exists");
				alert.showAndWait();
				return;
		    }
		    else
		    {
		    	users.users.get(theUser).albums.put(result.get(), new Album(result.get(), thePhotos));
		    	PaneSelector.setNewAlbumSelect(theUser);
		    }
		}

	}
}
