package application.controller.admin;


import javafx.event.ActionEvent;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Movie;
import service.MovieService;

public class InsertMovieController {
	
	private String errorBackground = "-fx-background-color: red;";
	private String insertOk = "-fx-background-color: green;";
	
	@FXML
	private Button insert;
	
	@FXML 
	private TextField movieId;
	
	@FXML 
	private TextField movieName;
	
	@FXML 
	private TextField runningTime;
	
	@FXML 
	private TextField rating;
	
    @FXML
    private Label message;
    
    private void setMessage (String messageToDisplay, String background) {
    	message.setStyle (background + " -fx-alignment: center;");
    	message.setText(messageToDisplay);
    }
	
	@FXML
	private void Insert (ActionEvent event) {
		Movie movieToInsert = new Movie ();
		MovieService movieS = new MovieService ();
		try {
			if (movieName.getText().isEmpty() ||
				movieId.getText().isEmpty()	|| 
				runningTime.getText().isEmpty() ||
				rating.getText().isEmpty()) {
				throw new Exception ("You must complete all fields");
			}
			if (!Objects.isNull(movieS.find(Integer.parseInt(movieId.getText())))) {
				throw new Exception ("A movie with id " + movieId.getText() + " already exist in database.");
			}
			movieToInsert.setMovieName(movieName.getText());
			movieToInsert.setMovieId(Integer.parseInt(movieId.getText()));
			movieToInsert.setRunningTime(Integer.parseInt(runningTime.getText()));
			movieToInsert.setRating(Float.parseFloat(rating.getText()));
			movieS.addMovie(movieToInsert);
			setMessage ("Movie insert in database", insertOk);
		} catch (Exception e){
			setMessage (e.getMessage(), errorBackground);
		}
		
	}
}
