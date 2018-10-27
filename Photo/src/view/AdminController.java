package view;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import storage.User;
import storage.Users;

/**
 * @authot Yuyang Chen  168008482
 * @author JAMES DEHART jrd250
 */
public class AdminController {
	
	@FXML
	public Button addUser;
	
	@FXML
	public Button deleteSelectedUser;
	
	@FXML
	public Button logOut;
	
	@FXML
	public ListView<String> listView;
	
	public Users users;
	private ObservableList<String> obsList;
	public String selectedItem;

    /**
     * start
     * @param u
     * @param mainStage
     */
    public void start(Users u, Stage mainStage)
	{
		users = u;
		
		obsList = FXCollections.observableArrayList();
		
		for(User a : users.users.values())
		{
			if(!a.userName.equals("admin"))
				obsList.add(a.userName);
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
		
		addUser.setOnMouseClicked((e) ->
		{
			addUser(mainStage);
		});
		
		deleteSelectedUser.setOnMouseClicked((e) ->
		{
			deleteSelectedUser(mainStage);
		});
		
		logOut.setOnMouseClicked((e) ->
		{
			logOut(mainStage);
		});
	}

    /**
     * selete item
     * @param mainStage
     */
    public void selectItem(Stage mainStage)
	{                
		selectedItem = listView.getSelectionModel().getSelectedItem();

	}

    /**
     * add user
     * @param mainStage
     */
    public void addUser(Stage mainStage)
	{
		TextInputDialog dialog = new TextInputDialog("");
		dialog.initOwner(mainStage); dialog.setTitle("Add User");
		dialog.setHeaderText("Enter a new username to create");
		dialog.setContentText("Enter name: ");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent())
		{
			if(users.users.containsKey(result.get()) || (result.get().equals("admin")) || (result.get().equals("stock")))
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
				users.users.put(result.get(), new User(result.get()));
				obsList.add(result.get());
			}
			
		}
	}

    /**
     * delete selected user
     * @param mainStage
     */
    public void deleteSelectedUser(Stage mainStage)
	{
		if(listView.getSelectionModel().getSelectedItem() == null)
			return;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("User Deletion Confirmation");
		alert.setHeaderText("Will delete user: " + listView.getSelectionModel().getSelectedItem());
		alert.setContentText("Are you sure you want to delete this User?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    if(listView.getSelectionModel().getSelectedItem().equals("admin") || listView.getSelectionModel().getSelectedItem().equals("stock"))
		    {
				/* error!*/
				Alert alert2 = new Alert(AlertType.ERROR);
				alert2.setTitle("User deletion error");
				alert2.setHeaderText("User deletion error");
				alert2.setContentText("The user cannot be deleted");
				alert2.showAndWait();
				return;
		    }
		    users.users.remove(listView.getSelectionModel().getSelectedItem());
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
}
