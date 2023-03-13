package application.controller.admin;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.User;
import service.UserService;

public class ListAllUsersController implements Initializable {
	
	String tabelStyle = "/application/style/tabelStyle.css";
	
	@FXML
	private TableView <User> tabelView;
	
	@FXML 
	private TableColumn <User, Integer> idColumn;
	
	@FXML 
	private TableColumn <User, String>  usernameColumn;
	
	@FXML 
	private TableColumn <User, String> roleColumn;
	
	@FXML 
	private TableColumn <User, Boolean> statusColumn;
	
	@FXML
	private Label message;
	
	@FXML
	private void enableUser (ActionEvent event) {
		try {
			if (tabelView.getSelectionModel().getSelectedItem().getIsEnable() == true) {
				throw new Exception ("User is already enabled");
			} else {
				tabelView.getSelectionModel().getSelectedItem().setIsEnable(true);
				UserService userS = new UserService ();
				userS.updateUser(tabelView.getSelectionModel().getSelectedItem());
				message.setText("User was enabled");
			}
		} catch (Exception e) {
			System.out.println(e);
			message.setText(e.getMessage());
		}
	}
	
	@FXML
	private void disableUser (ActionEvent event) {
		try {
			if (tabelView.getSelectionModel().getSelectedItem().getIsEnable() == false) {
				throw new Exception ("User is already disabled");
			}
			if (tabelView.getSelectionModel().getSelectedItem().getUserName().equals("admin")) {
				throw new Exception ("Main Admin can't be disabled");
			}
			else {
				tabelView.getSelectionModel().getSelectedItem().setIsEnable(false);
				UserService userS = new UserService ();
				userS.updateUser(tabelView.getSelectionModel().getSelectedItem());
				message.setText("User was disabled");
			}
		} catch (Exception e) {
			System.out.println(e);
			message.setText(e.getMessage());
		}
	}
	
	private void loadDate () {
		idColumn.setCellValueFactory(new PropertyValueFactory <> ("userCod"));
		usernameColumn.setCellValueFactory(new PropertyValueFactory <> ("userName"));
		roleColumn.setCellValueFactory(new PropertyValueFactory <> ("userRole"));
		statusColumn.setCellValueFactory(new PropertyValueFactory <> ("isEnable"));
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		UserService userS = new UserService ();
		ObservableList<User> userList = FXCollections.observableList(userS.getAllUsers());
		loadDate();
		tabelView.setItems(userList);
		tabelView.getStylesheets().add(tabelStyle);
		message.setText("Select an user to modify it");
	}
}
