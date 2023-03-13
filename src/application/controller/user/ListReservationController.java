package application.controller.user;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;


import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import model.Movieevent;
import model.Reservation;
import service.EventService;
import service.ReservationService;
import service.generic.Mediator;

public class ListReservationController implements Initializable {
	private final String DATE_FORMAT = "dd.MMM.YYYY HH:mm";
	String tabelStyle = "/application/style/tabelStyle.css";
	
	private Reservation reservation;
	ReservationService reservationS = new ReservationService ();
	EventService eventS = new EventService ();
	Movieevent eventToUpdate = new Movieevent ();
	
	@FXML
	private TableView <Reservation> tabelView;
	
    @FXML
    private Label errorMessage;
    
    @FXML
    private Button cancelReservation;
    
    @FXML
    private AnchorPane root;
	
	@FXML 
	private TableColumn <Reservation, String> movieColumn;
	
	@FXML 
	private TableColumn <Reservation, Date>  dateColumn;
	
	@FXML 
	private TableColumn <Reservation, String> codeColumn;
	
	@FXML 
	private TableColumn <Reservation, Float> priceColumn;
	
    @FXML
    private TableColumn<Reservation, String> formatDate;

	private void loadData () {
		movieColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMovieevent().getMovie().getMovieName()));
		codeColumn.setCellValueFactory(p -> new SimpleStringProperty (p.getValue().getReservationNo()));
		priceColumn.setCellValueFactory(date -> {
			return new SimpleObjectProperty<>(date.getValue().getTotalPrice());
		});
		dateColumn.setCellValueFactory(date -> {
			return new SimpleObjectProperty<>(date.getValue().getMovieevent().getEventDate());
		});
		formatDate.setCellValueFactory(date -> {
			Date dateToFormat = date.getValue().getMovieevent().getEventDate();
			SimpleDateFormat dateFormat = new SimpleDateFormat (DATE_FORMAT);
			String formatedDate = dateFormat.format(dateToFormat);
			return new SimpleStringProperty (formatedDate);
		});
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			ObservableList <Reservation> reservationList = FXCollections.observableList(reservationS.findMyReservations(Mediator.getInstance().getUser()));
			loadData ();
			tabelView.setItems(reservationList);
			tabelView.getStylesheets().add(tabelStyle);
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
    @FXML
    private void cancelReservation (ActionEvent event) {
    	reservation = tabelView.getSelectionModel().getSelectedItem();
    	try {
    		if (reservation.getMovieevent().getEventDate().compareTo(new Date ()) < 0) {
    			throw new Exception ("Event is passed");
    		}
    		eventToUpdate = reservation.getMovieevent();
    		eventToUpdate.setPlaces(eventToUpdate.getPlaces() + reservation.getQuantity());
    		eventS.update(tabelView.getSelectionModel().getSelectedItem().getMovieevent());
    		reservationS.deleteReservation(reservation);
    		errorMessage.setText ("You cancel the reservation");
    	} catch (Exception e) {
    		errorMessage.setText(e.getMessage());
    	}
    }
}
