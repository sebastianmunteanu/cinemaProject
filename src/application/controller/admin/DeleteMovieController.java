package application.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import service.MovieService;

public class DeleteMovieController {
	
	@FXML
	private TextField movieId;
	
	@FXML 
	private Label message;
	
	@FXML
	private void Delete (ActionEvent event) {
		MovieService movieS = new MovieService ();
		try {
			movieS.removeMovie(Integer.parseInt(movieId.getText()));
			message.setText("Movie with id " + movieId.getText() + " was deleted");
		} catch (Exception e) {
			message.setText(e.getMessage());
		}
	}
}
