package view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import storage.User;
import storage.Users;

/**
 * @authot Yuyang Chen  168008482
 * @author JAMES DEHART jrd250
 */
public class CreateNewUserController {
	
	@FXML
	public TextField textField;
	@FXML
	public Text errorText;
	@FXML
	public Button backButton;
	@FXML
	public Button submitButton;
	
	Users users;

    /**
     * start
     * @param u
     * @param mainStage
     */
    public void start(Users u, Stage mainStage)
	{
		users = u;
		
		backButton.setOnMouseClicked((e) -> {
			back(mainStage);
		});
		submitButton.setOnMouseClicked((e) -> {
			submit(mainStage);
		});
	}

    /**
     * handle submit
     * @param mainStage
     */
    public void submit(Stage mainStage)
	{
		if( (users.users.containsKey(textField.getText())) || (textField.getText().equals("admin")) || (textField.getText().equals("stock")))
			errorText.setText("Error, selected username already exists");
		else
		{
			users.users.put(textField.getText(), new User(textField.getText()));
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setHeaderText("User account successfully created");
			alert.setContentText("The user account was successfully created, login to use your account");
			alert.showAndWait();
			PaneSelector.setOldLogin();
		}
	}

    /**
     * handle back
     * @param mainStage
     */
    public void back(Stage mainStage)
	{
		PaneSelector.setOldLogin();
	}
}
