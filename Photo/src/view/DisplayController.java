package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import storage.Photo;
import storage.Users;

/**
 * @authot Yuyang Chen  168008482
 * @author JAMES DEHART jrd250
 */
public class DisplayController {

	@FXML
	Text caption;
	
	@FXML
	Button recaption;
	
	@FXML
	Button addTag;
	
	@FXML
	Button deleteTag;
	
	@FXML
	Button backToGallery;
	
	@FXML
	Button nextPhoto;
	
	@FXML
	Button previousPhoto;
	
	@FXML
	ScrollPane scrollPane;
	
	@FXML
	Text dateText;
	
	@FXML
	Text tagText;
	
	Users users;
	String theUser;
	String theAlbum;
	Integer theIndex;

    /**
     * start
     * @param u
     * @param primaryStage
     * @param name
     * @param album
     * @param index
     */
    public void start(Users u, Stage primaryStage, String name, String album, int index)
	{
		users = u;
		theUser = name;
		theAlbum = album;
		theIndex = index;
		
		caption.setWrappingWidth(147);
		caption.setText(users.users.get(name).albums.get(album).photos.get(index).caption);
		
		scrollPane.setContent(users.users.get(name).albums.get(album).photos.get(index).getSlideShowView());

		dateText.setText(users.users.get(name).albums.get(album).photos.get(index).getLastModifiedDate());
		
		
		setTags(users.users.get(name).albums.get(album).photos.get(index));
		
		recaption.setOnMouseClicked((e) ->
		{
			recaption(primaryStage);
		});
		
		previousPhoto.setOnMouseClicked((e) ->
		{
			previousPhoto(primaryStage);
		});
		
		nextPhoto.setOnMouseClicked((e) ->
		{
			nextPhoto(primaryStage);
		});
		
		backToGallery.setOnMouseClicked((e) ->
		{
			backToGallery(primaryStage);
		});
		
		addTag.setOnMouseClicked((e) ->
		{
			addTag(primaryStage);
		});
		
		deleteTag.setOnMouseClicked((e) ->
		{
			deleteTag(users.users.get(name).albums.get(album).photos.get(index));
		});
	}

    /**
     * set tags
     * @param p
     */
    public void setTags(Photo p)
	{
		String bigString = "";
		for(String i : p.tags.keySet())
		{
			
			
			bigString += i + ":\n";
			bigString += p.tags.get(i) + "\n\n";
			
		}
		
		tagText.setText(bigString);
	}

    /**
     * handle recaption
     * @param primaryStage
     */
    public void recaption(Stage primaryStage)
	{
		TextInputDialog dialog = new TextInputDialog("Example Caption");
		dialog.setTitle("Enter Caption");
		dialog.setHeaderText("Enter Caption");
		dialog.setContentText("Please enter a caption for the photo");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    caption.setText(result.get());
		    users.users.get(theUser).albums.get(theAlbum).photos.get(theIndex).caption = result.get();
		}
	}

    /**
     * previousPhoto
     * @param primaryStage
     */
    public void previousPhoto(Stage primaryStage)
	{
		if(theIndex == 0)
			return;
		caption.setText(users.users.get(theUser).albums.get(theAlbum).photos.get(theIndex - 1).caption);
		scrollPane.setContent(users.users.get(theUser).albums.get(theAlbum).photos.get(theIndex - 1).getSlideShowView());
		dateText.setText(users.users.get(theUser).albums.get(theAlbum).photos.get(theIndex - 1).getLastModifiedDate());
		setTags(users.users.get(theUser).albums.get(theAlbum).photos.get(theIndex - 1));
		--theIndex;
	}

    /**
     * next photo
     * @param primaryStage
     */
    public void nextPhoto(Stage primaryStage)
	{
		if(theIndex == users.users.get(theUser).albums.get(theAlbum).photos.size() - 1)
			return;
		
		caption.setText(users.users.get(theUser).albums.get(theAlbum).photos.get(theIndex + 1).caption);
		scrollPane.setContent(users.users.get(theUser).albums.get(theAlbum).photos.get(theIndex + 1).getSlideShowView());
		dateText.setText(users.users.get(theUser).albums.get(theAlbum).photos.get(theIndex + 1).getLastModifiedDate());
		setTags(users.users.get(theUser).albums.get(theAlbum).photos.get(theIndex + 1));
		++theIndex;
	}

    /**
     * back to gallery
     * @param primaryStage
     */
    public void backToGallery(Stage primaryStage)
	{
		if(theUser.equals("admin"))
			PaneSelector.setOldSearchGallery();
		else
			PaneSelector.setOldGallery();
	}

    /**
     * add tag
     * @param primaryStage
     */
    public void addTag(Stage primaryStage)
	{
		Photo thePhoto = users.users.get(theUser).albums.get(theAlbum).photos.get(theIndex);

		
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Add/Edit Tag");
		dialog.setHeaderText("Choose a tag and then set it's value");


		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);


		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		ChoiceBox<String> keys = new ChoiceBox<String>(FXCollections.observableArrayList(Photo.tagTypes));
		keys.getSelectionModel().select(0);
		/* get used tags */

		
		
		
		TextField value = new TextField();
		value.setPromptText("Value");

		grid.add(new Label("Key:"), 0, 0);
		grid.add(keys, 1, 0);
		grid.add(new Label("Value:"), 0, 1);
		grid.add(value, 1, 1);


		dialog.getDialogPane().setContent(grid);


		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == ButtonType.OK) {
		        return new Pair<>(keys.getSelectionModel().getSelectedItem(), value.getText());
		    }
		    return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();
		
		
		result.ifPresent(keyVal -> {
			thePhoto.tags.put(keyVal.getKey(), keyVal.getValue());
			setTags(thePhoto);
		});
	}

    /**
     * delete tag
     * @param p
     */
    public void deleteTag(Photo p)
	{
		List<String> keyValues = new ArrayList<String>();
		for(String i : p.tags.keySet())
		{
			keyValues.add(i + ": " + p.tags.get(i));
		}
		
		if(keyValues.isEmpty())
			return;
		
		ChoiceDialog<String> dialog = new ChoiceDialog<String>(keyValues.get(0), keyValues);
		
		dialog.setTitle("Delete Tag");
		
		dialog.setHeaderText("Select tag & value combo to delete");
		
		dialog.setContentText("Delete: ");
		
		Optional<String> result = dialog.showAndWait();
		
		if (result.isPresent()){			
		   p.tags.remove(result.get().split(":")[0]);
		   setTags(p);
		}
	}
}
