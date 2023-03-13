package application.controller;


import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import service.UserService;

public class SignUpController implements Initializable {
	
	@FXML
	private AnchorPane root;
	
	@FXML
	private TextField firstName;
	
	@FXML
	private TextField lastName;
	
	@FXML
	private TextField userName;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private PasswordField passwordCheck;
	
	@FXML
	private TextField email;
	
	@FXML
	private Button submitButton;
	
	@FXML
	private ComboBox<String> roleList;
	
	@FXML
	private Label errorMessage;
	
	@FXML
	private Button exit;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		roleList.getItems().setAll("Admin", "User", "Cashier");
		exit.setOnAction((event)->{
			Stage stage = (Stage) root.getScene().getWindow();
			stage.close();
		});
	}
	
	@FXML
	private void Submit (ActionEvent event) {
		UserService userS = new UserService();
		boolean readyToInsert = false;
		User userToInsert = new User();
		
		try {
			if (firstName.getText().length() == 0 || lastName.getText().length() == 0 || email.getText().length() == 0) {
				throw new Exception ("You let blank fields");
			} else {
				userToInsert.setFirstName(firstName.getText());
				userToInsert.setLastName(lastName.getText());
				userToInsert.setEmail(email.getText());
			}
			
			if (password.getText().equals(passwordCheck.getText()) && password.getText().length() > 3) {
				//check for password complexity
				userToInsert.setUserPassword(password.getText());
			} else {
				throw new Exception ("Password not the same");
			}
			
			if (userName.getText().length() < 4) {
				throw new Exception ("Your username must be more than 3 characters");
			} else {
				if (!Objects.isNull(userS.findIfExist(userName.getText()))) {
					throw new Exception ("User name isn't available");
				} else {
					userToInsert.setUserName(userName.getText());
				}
			}

			if (roleList.getValue() == null) {
				throw new Exception ("You must select a role");
			} else {
				userToInsert.setUserRole(roleList.getValue());
				if (roleList.getValue() == "User") {
					userToInsert.setIsEnable(true);
				}
				userToInsert.setSignInDate(new Date());
				readyToInsert = true;
			}
		} catch (Exception exception) {
			errorMessage.setText(exception.getMessage());
		}
		if (readyToInsert) {
			userS.addUser(userToInsert);
			Stage stage = (Stage) root.getScene().getWindow();
			stage.close();
		}
	}
}
