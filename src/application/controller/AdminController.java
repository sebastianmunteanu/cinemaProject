package application.controller;

import java.net.URL;

import javafx.animation.ScaleTransition;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import service.generic.LogOut;
import service.generic.Mediator;

public class AdminController implements Initializable {
	private Button activeButton;
	private Button pressedButton;
	ScaleTransition pulse;
	
	// layouts sources
	private String insertL = "/application/layout/admin/InsertMovie.fxml";
	private String deleteL = "/application/layout/admin/DeleteMovie.fxml";
	private String moviesL = "/application/layout/admin/ListAllMovies.fxml";
	private String usersL = "/application/layout/admin/ListAllUsers.fxml";
	private String eventsL = "/application/layout/admin/ListAllEvents.fxml";
	//style source
	String buttonStyle = "/application/style/ButtonLayout.css";

	@FXML
	private Button insertMovie;

	@FXML
	private Button deleteMovie;

	@FXML
	private Button listMovies;

	@FXML
	private Button listUsers;

	@FXML
	private Button listEvents;

	@FXML
	private AnchorPane center;
	
	@FXML
	private Button logOut;

	@FXML
	private BorderPane root;
	
    @FXML
    private AnchorPane movieInfo;
    
    @FXML
    private Label movieName;
    
    @FXML
    private Label totalEvents;
    
    @FXML
    private Label totalIncome;
    
    @FXML
    private Label directBuy;
    
    @FXML
    private Label reservationIncome;

	public void display(String layout) {
		try {
			Parent insertLayout = (Parent) FXMLLoader.load(getClass().getResource(layout));
			center.getChildren().setAll(insertLayout);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
    private void startPulse(Button btn) {
        pulse = new ScaleTransition(Duration.seconds(2), btn);
        pulse.setFromX(1);
        pulse.setFromY(1);
        pulse.setToX(1.3);
        pulse.setToY(1.3);
        pulse.setCycleCount(ScaleTransition.INDEFINITE);
        pulse.setAutoReverse(true);
        pulse.play();
    }
    
    private void stopPulse(Button btn) {
    	btn.setScaleX(1);
    	btn.setScaleY(1);
        pulse.stop();
    }
    
    private void animeButton (Button pressedButton) {
    	pressedButton.setStyle("-fx-background-color: green;");
    	if (activeButton == null) {
    		activeButton = pressedButton;
    		startPulse(activeButton);
    	}
    	if (activeButton != pressedButton) {
    		stopPulse(activeButton);
    		activeButton.setStyle("-fx-background-color: white;");
    		activeButton = pressedButton;
    		startPulse(pressedButton);
    	} 
    }
    
    private void pulseButton (Button btn) {
		movieInfo.setVisible(false);
		pressedButton = btn;
		animeButton (pressedButton);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		movieInfo.setVisible(false);
		root.getStylesheets().add(buttonStyle);
		insertMovie.setOnAction((event) -> {
			pulseButton (insertMovie);
			display(insertL);
		});
		deleteMovie.setOnAction((event) -> {
			pulseButton (deleteMovie);
			display(deleteL);
		});
		listMovies.setOnAction((event) -> {
			Mediator.getInstance().setMoveInfo(movieInfo);
			pulseButton (listMovies);
			display(moviesL);
		});
		listUsers.setOnAction((event) -> {
			pulseButton (listUsers);
			display(usersL);
		});
		listEvents.setOnAction((event) -> {
			pulseButton (listEvents);
			display(eventsL);
		});
		logOut.setOnAction((event) -> {
			LogOut <BorderPane>  logOut = new LogOut <BorderPane> ();
			logOut.logOut(root);
		});
	}
}
