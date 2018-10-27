package view;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import storage.Users;

/**
 * @authot Yuyang Chen  168008482
 * @author JAMES DEHART jrd250
 */
public class LoginController {
	
	@FXML
	public TextField userNameTextField;
	@FXML
	public Button newUserButton;
	@FXML
	public Button loginButton;
	
	public Users users;

    /**
     * start
     * @param u
     * @param mainStage
     */
    public void start(Users u, Stage mainStage)
	{
		users = u;
		
		newUserButton.setOnMouseClicked((e) -> {
			newUser(mainStage);
		});
		loginButton.setOnMouseClicked((e) -> {
			login(mainStage);
		});
	}
	
	public void newUser(Stage mainStage)
	{
		PaneSelector.setNewCreateNewUser();
	}

    /**
     * log in
     * @param mainStage
     */
    public void login(Stage mainStage)
	{
		/* First check if root (special case) */
		if(userNameTextField.getText().equals("admin"))
		{
			PaneSelector.setNewAdmin();
		}
		else if(!users.users.containsKey(userNameTextField.getText()))
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Login Error");
			alert.setHeaderText("Login Error");
			alert.setContentText("The username entered does not belong to any account");
			alert.showAndWait();
		}
		else
		{
			PaneSelector.setNewAlbumSelect(userNameTextField.getText());
			
		}
	}
}
