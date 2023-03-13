package application.controller;


import application.Main;
import application.windows.SignUpWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.User;
import service.UserService;
import service.generic.Mediator;

public class MainWindowController {

	@FXML
	private Button logIn;
	
	@FXML
	private AnchorPane logInForm;
	
	@FXML
	private TextField username;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private Label logInMessage;
	
	@FXML
	private void logInClick (ActionEvent event) {
		User logInUser = new User ();
		try {
			UserService logInS = new UserService ();
			if (username.getText().equals("") || password.getText().equals("")) {
				throw new Exception ("You let blank fields!");
			}
			logInUser = logInS.findUser(username.getText(), password.getText());
			if (!logInUser.getIsEnable()) {
				throw new Exception ("User is disable! Wait for approval.");
			}
			checkUserType(logInUser);
		} catch (Exception e) {
			logInMessage.setText(e.getMessage());
		}
	}
	
	@FXML
	private void signUpClick (ActionEvent e) {
		logInMessage.setText("");
		SignUpWindow signIn = new SignUpWindow();
		signIn.displayForm();
	}
	
	private void checkUserType (User logInUser){
		//String testLayout = "/application/layout/test.fxml";
		String adminLayout = "/application/layout/AdminWindow.fxml";
		String userLayout = "/application/layout/UserWindow.fxml";
		String cashierLayout = "/application/layout/CashierWindow.fxml";
		String cashTitle = "You are log in as Cashier";
		String userTitle = "You are log in as USER";
		String adminTitle = "You are log in as ADMINISTRATOR";
		double width = 1500;
		double height = 700;
		Main m = new Main();
		Mediator.getInstance().setUser(logInUser);
		if (logInUser.getUserRole().equals("Admin")) {
			try {
				m.changeScene(adminLayout, adminTitle, 1500, 700);
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (logInUser.getUserRole().equals("User")) {
			try {
				m.changeScene(userLayout, userTitle, 1500, 700);
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			try {
				m.changeScene(cashierLayout, cashTitle, width, height);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
}
