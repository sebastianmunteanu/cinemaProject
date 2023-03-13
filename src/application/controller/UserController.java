package application.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import service.generic.LogOut;

public class UserController implements Initializable {
	
	String buttonStyle = "/application/style/ButtonLayout.css";
	private String eventsL = "/application/layout/admin/ListAllEvents.fxml";
	private String reservationsL = "/application/layout/user/ListReservation.fxml";
	
	@FXML
	private GridPane root;
	
	@FXML
	private AnchorPane listField;
	
	@FXML
	private Button logOut;
	
	@FXML
	private Button listMovies;
	
	@FXML
	private Button listReservations;
	
	public void display(String layout) {
		try {
			Parent insertLayout = (Parent) FXMLLoader.load(getClass().getResource(layout));
			listField.getChildren().setAll(insertLayout);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		root.getStylesheets().add(buttonStyle);
		logOut.setOnAction((event) -> {
			LogOut <GridPane>  logOut = new LogOut <GridPane> ();
			logOut.logOut(root);
		});
		
		listMovies.setOnAction((event) -> {
			display(eventsL);
		});
		
		listReservations.setOnAction((event) -> {
			display(reservationsL);
		});		
	}
}
