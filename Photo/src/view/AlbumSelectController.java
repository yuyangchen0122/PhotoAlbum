package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import storage.Album;
import storage.Users;
import storage.Photo;

/**
 * @authot Yuyang Chen  168008482
 * @author JAMES DEHART jrd250
 */
public class AlbumSelectController {
	
	@FXML
	public Button openAlbum;
	
	@FXML
	public Button renameAlbum;
	
	@FXML
	public Button deleteAlbum;
	
	@FXML
	public Button createAlbum;
	
	@FXML
	public Button logOut;
	
	@FXML
	public ListView<String> listView;
	
	@FXML
	public Button dateSearch;
	
	@FXML
	public Button tagSearch;
	
	public String selectedItem;
	
	public Users users;
	public String  theUser;
	
	private ObservableList<String> obsList;

    /**
     * start
     * @param u
     * @param mainStage
     * @param user
     */
    public void start(Users u, Stage mainStage, String user)
	{
		users = u;
		theUser = user;
		/* Get the albums */

		obsList = FXCollections.observableArrayList();
		
		for(Album a : users.users.get(user).albums.values())
		{
			obsList.add(a.name);
		}	
		listView.setItems(obsList);

		
		if(obsList.size() > 0)
		{
			listView.getSelectionModel().select(0);
			selectedItem = listView.getSelectionModel().getSelectedItem();
		}
		
		listView
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				selectItem(mainStage)
				);
		
		createAlbum.setOnMouseClicked((e) ->
		{
			createAlbum(mainStage);
		});
		
		renameAlbum.setOnMouseClicked((e) ->
		{
			renameAlbum(mainStage);
		});
		
		deleteAlbum.setOnMouseClicked((e) ->
		{
			deleteAlbum(mainStage);
		});
		
		logOut.setOnMouseClicked((e) ->
		{
			logOut(mainStage);
		});	
		
		openAlbum.setOnMouseClicked((e) ->
		{
			openAlbum(mainStage);
		});	
		
		dateSearch.setOnMouseClicked((e) ->
		{
			dateSearch(mainStage);
		});	
		
		tagSearch.setOnMouseClicked((e) ->
		{
			tagSearch(mainStage);
		});	
	}

    /**
     * select item
     * @param mainStage
     */
    public void selectItem(Stage mainStage)
	{                
		selectedItem = listView.getSelectionModel().getSelectedItem();

	}

    /**
     * create album
     * @param mainStage
     */
    public void createAlbum(Stage mainStage)
	{
		TextInputDialog dialog = new TextInputDialog("");
		dialog.initOwner(mainStage); dialog.setTitle("Create Album");
		dialog.setHeaderText("Enter album name");
		dialog.setContentText("Enter name: ");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent())
		{
			if(users.users.get(theUser).albums.containsKey(result.get()))
			{
				/* error!*/
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Album naming Error");
				alert.setHeaderText("Album naming error");
				alert.setContentText("The album name entered already exists");
				alert.showAndWait();
				return;
			}
			else
			{
				users.users.get(theUser).albums.put(result.get(), new Album(result.get()));
				obsList.add(result.get());
			}
			
		}
	}

    /**
     * rename album
     * @param mainStage
     */
    public void renameAlbum(Stage mainStage)
	{
		if(listView.getSelectionModel().getSelectedItem() == null)
			return;
		TextInputDialog dialog = new TextInputDialog(listView.getSelectionModel().getSelectedItem());
		dialog.initOwner(mainStage); dialog.setTitle("Rename Album");
		dialog.setHeaderText("Enter new album name");
		dialog.setContentText("Enter name: ");

		Optional<String> result = dialog.showAndWait();
		
		if(result.isPresent())
		{
			Album tmp = users.users.get(theUser).albums.get(listView.getSelectionModel().getSelectedItem());
			users.users.get(theUser).albums.remove(listView.getSelectionModel().getSelectedItem());
			tmp.name = result.get();
			users.users.get(theUser).albums.put(tmp.name, tmp);
			obsList.set(listView.getSelectionModel().getSelectedIndex(), tmp.name);
		}
	}

    /**
     * delete album
     * @param mainStage
     */
    public void deleteAlbum(Stage mainStage)
	{
		if(listView.getSelectionModel().getSelectedItem() == null)
			return;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Album Deletion Confirmation");
		alert.setHeaderText("Will delete album: " + listView.getSelectionModel().getSelectedItem());
		alert.setContentText("Are you sure you want to delete this Album?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    users.users.get(theUser).albums.remove(listView.getSelectionModel().getSelectedItem());
			obsList.remove(listView.getSelectionModel().getSelectedIndex());
		} else {
		    return;
		}
	}

    /**
     * log out
     * @param mainStage
     */
    public void logOut(Stage mainStage)
	{
		PaneSelector.setOldLogin();
	}

    /**
     * open album
     * @param mainStage
     */
    public void openAlbum(Stage mainStage)
	{
		if(listView.getSelectionModel().getSelectedItem() == null)
			return;
		PaneSelector.setNewGallery(theUser, listView.getSelectionModel().getSelectedItem());
	}

    /**
     * date search
     * @param mainStage
     */
    public void dateSearch(Stage mainStage)
	{
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Search by Date");
		dialog.setHeaderText("Choose a start date and end date.\nResulting photos will be placed in album form.\n(Includes start and end date)");

		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		DatePicker datePicker1 = new DatePicker();
		DatePicker datePicker2 = new DatePicker();
		
		TextField value = new TextField();
		value.setPromptText("Value");

		grid.add(new Label("Start:"), 0, 0);
		grid.add(datePicker1, 1, 0);
		grid.add(new Label("End:"), 0, 1);
		grid.add(datePicker2, 1, 1);


		dialog.getDialogPane().setContent(grid);
		
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == ButtonType.OK) {
		        return new Pair<>(datePicker1.getValue().toString(), datePicker2.getValue().toString());
		    }
		    return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();
		
		
		result.ifPresent(keyVal -> {
			
			/* first check that dates are cronological */

			ArrayList<Photo> goodPhotos = new ArrayList<Photo>();
			String[] tmp;
			int year1; int year2; int month1; int month2; int day1; int day2;
			
			
			tmp = keyVal.getKey().split("-");
			year1 = Integer.parseInt(tmp[0]);
			month1 = Integer.parseInt(tmp[1]);
			day1  = Integer.parseInt(tmp[2]);
			
			tmp = keyVal.getValue().split("-");
			year2 = Integer.parseInt(tmp[0]);
			month2 = Integer.parseInt(tmp[1]);
			day2  = Integer.parseInt(tmp[2]);
			
			if( (year1 > year2) || ((year1 == year2) && (month1 > month2)) || ((year1 == year2) && (month1 == month2) && (day1 > day2)))
				return;
			
			for(Album a : users.users.get(theUser).albums.values())
			{
				for(Photo p : a.photos)
				{
					if(p.aboveDate(year1, month1, day1) && p.belowDate(year2, month2, day2))
						goodPhotos.add(p);
				}
			}
			PaneSelector.setNewSearchGallery(goodPhotos, theUser);
		});
	}

    /**
     * tag search
     * @param mainStage
     */
    public void tagSearch(Stage mainStage)
	{
		Integer numTags;
		boolean isAnd = false;
		List<Integer> choices = new ArrayList<>();
		choices.add(1);
		choices.add(2);
		choices.add(3);
		choices.add(4);
		choices.add(5);
		choices.add(6);
		choices.add(7);

		ChoiceDialog<Integer> dialog = new ChoiceDialog<>(1, choices);
		dialog.setTitle("Select Number of tags to match");
		dialog.setHeaderText("Select Number of tags to match");
		dialog.setContentText("Choose your number:");

		
		Optional<Integer> result = dialog.showAndWait();
		if (result.isPresent()){
		    numTags = result.get();
		    
		    List<String> andOr = new ArrayList<>();
		    andOr.add("and");
		    andOr.add("or");
		    
		    ChoiceDialog<String> parity = new ChoiceDialog<>("and", andOr);
		    parity.setTitle("Select And or OR");
		    parity.setHeaderText("Select whether the tags must all be in the same photo (AND)\n or if only one tag needs to be in a photo (OR)");
		    parity.setContentText("Choose:");
		    
		    Optional<String> resultString = parity.showAndWait();
		    
		    if(resultString.isPresent())
		    {
		    	if(resultString.get().equals("and"))
		    		isAnd = true;
		    	else
		    		isAnd = false;
		    	
		    	
		    }
		    else return;
		    
		}
		else return;
		
		/* real shit starts here */
		Dialog<ArrayList<Pair<String, String>>> tagDialog = new Dialog<>();
		tagDialog.setTitle("Search by Tags");
		tagDialog.setHeaderText("Select the tags you want to search on:");

		tagDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		//DatePicker datePicker1 = new DatePicker();
		//DatePicker datePicker2 = new DatePicker();
		
		ArrayList<Pair<ChoiceBox<String>, TextField>> cBoxes = new ArrayList<Pair<ChoiceBox<String>, TextField>>();
		
		for(int i = 0; i < numTags; ++i)
		{
			cBoxes.add(new Pair<>(new ChoiceBox<String>(FXCollections.observableArrayList(Photo.tagTypes)), new TextField("")));
			grid.add(new Label("tag#" + Integer.toString(i)), 0, (i*2));
			grid.add(cBoxes.get(cBoxes.size() - 1).getKey(), 1, (i*2));
			grid.add(cBoxes.get(cBoxes.size() - 1).getValue(), 1, (i*2)+1);
		}
		
		//TextField value = new TextField();
		//value.setPromptText("Value");


		tagDialog.getDialogPane().setContent(grid);
		
		tagDialog.setResultConverter(dialogButton -> {
		    if (dialogButton == ButtonType.OK) {
		    	ArrayList<Pair<String,String>> tmp = new ArrayList<Pair<String,String>>();
		    	for(int i = 0; i < numTags; ++i)
		    	{
		    		tmp.add(new Pair<>(cBoxes.get(i).getKey().getValue(), cBoxes.get(i).getValue().getText()));
		    	}
		        //return new Pair<>(datePicker1.getValue().toString(), datePicker2.getValue().toString());
		    	return tmp;
		    }
		    return null;
		});

		Optional<ArrayList<Pair<String, String>>> result2 = tagDialog.showAndWait();
		final boolean isAnd2 = isAnd;
		result2.ifPresent(keyVal -> {
			ArrayList<Photo> goodPhotos = new ArrayList<Photo>();
			boolean goodFit = true;
			
			if(isAnd2 == true)
			{
				for(Album a : users.users.get(theUser).albums.values())
				{
					for(Photo p : a.photos)
					{
						for(Pair<String, String> pair : keyVal)
						{
							if(p.tags.containsKey(pair.getKey()))
							{
								if(p.tags.get(pair.getKey()).equals(pair.getValue()))
									continue;
							}
							goodFit = false;
						}
						if(goodFit)
							goodPhotos.add(p);
						goodFit = true;
					}
				}
			}
			else
			{
				for(Album a : users.users.get(theUser).albums.values())
				{
					for(Photo p : a.photos)
					{
						goodFit = false;
						for(Pair<String, String> pair : keyVal)
						{
							if(p.tags.containsKey(pair.getKey()))
							{
								if(p.tags.get(pair.getKey()).equals(pair.getValue()))
									goodFit = true;
							}
						}
						if(goodFit)
							goodPhotos.add(p);
					}
				}
			}
			
			PaneSelector.setNewSearchGallery(goodPhotos, theUser);
		});
	}
}
