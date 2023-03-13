package application.controller.admin;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Movieevent;
import model.Reservation;
import service.EventService;
import service.ReservationS;
import service.generic.Mediator;

public class ListAllEventsController implements Initializable {
	
	String tabelStyle = "/application/style/tabelStyle.css";
	private Movieevent selectedEvent = new Movieevent();
	
	@FXML
	private TableView <Movieevent> tabelView;
	
	@FXML
	private TableColumn <Movieevent, String> movieName;
	
	@FXML
	private TableColumn <Movieevent, Date> eventDate;
	
	@FXML
	private TableColumn <Movieevent, Float> price;
	
    @FXML
    private TableColumn<Movieevent, Integer> places;
    
    @FXML
    private TextField quantity;
    
    @FXML
    private Label message;
	
	@FXML
	private AnchorPane reservation;

	private void loadDate () {
		movieName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMovie().getMovieName()));
		eventDate.setCellValueFactory(new PropertyValueFactory <> ("eventDate"));
		price.setCellValueFactory(new PropertyValueFactory <> ("price"));
		places.setCellValueFactory(new PropertyValueFactory <> ("places"));
	}
	
	private void setTicket (AnchorPane ticket) {
		//movie name
		Label movieName = (Label) Mediator.getInstance().getTicketGenerator().getChildren().get(0);
		movieName.setText(selectedEvent.getMovie().getMovieName());
		
		//client name
		Label clientName = (Label) Mediator.getInstance().getTicketGenerator().getChildren().get(1);
		clientName.setText("");
		
		//quantity
		Label quantity = (Label) Mediator.getInstance().getTicketGenerator().getChildren().get(2);
		quantity.setText("");
		TextField insertQuantity = (TextField) Mediator.getInstance().getTicketGenerator().getChildren().get(9);
		
		//total price
		Label totalPrice = (Label) Mediator.getInstance().getTicketGenerator().getChildren().get(3);
		totalPrice.setText("0.0");
		insertQuantity.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				newValue = String.valueOf(Integer.parseInt(insertQuantity.getText()) * selectedEvent.getPrice());
				totalPrice.setText(newValue);
				quantity.setText(insertQuantity.getText());
			} catch (Exception e) {
				totalPrice.setText("0.0");
				quantity.setText("");
				System.out.println(e);
			}
		});
	}
	
	private Reservation setReservation () throws Exception {
		Reservation reservation = new Reservation ();
		try {
			String reservationNumber =  String.valueOf(Calendar.DAY_OF_YEAR) +
				String.valueOf(Calendar.HOUR) +
				String.valueOf(selectedEvent.getEventId()) +
				String.valueOf(selectedEvent.getCinemahall().getHallId()) +
				String.valueOf(selectedEvent.getPlaces());
		reservation.setUser(Mediator.getInstance().getUser());
		reservation.setQuantity(Integer.parseInt(quantity.getText()));
		reservation.setReservationNo(reservationNumber);
		reservation.setMovieevent(selectedEvent);
		reservation.setReservationDate(new Date ());
		reservation.setTotalPrice(selectedEvent.getPrice() * Integer.parseInt(quantity.getText()));
		} catch (Exception e) {
			throw new Exception ("Insert quantity");
		}
		return reservation;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		reservation.setVisible(false);
		EventService eventS = new EventService ();
		ObservableList<Movieevent> eventList = FXCollections.observableList(eventS.getAllEvents());
		ObservableList<Movieevent> activeEvent = FXCollections.observableArrayList();
		loadDate ();
		eventList.forEach ((node) -> {
			if (node.getEventDate().compareTo(new Date ()) > 0) {
				activeEvent.add(node);
			}
		});
		if (Mediator.getInstance().getUser().getUserRole().equals("Admin")) {
			tabelView.setItems(eventList);
		} else {
			tabelView.setItems(activeEvent);
		}
		tabelView.getStylesheets().add(tabelStyle);
	}
	
	
	@FXML 
	private void getItem (MouseEvent event) {
		selectedEvent = tabelView.getSelectionModel().getSelectedItem();
		if (Mediator.getInstance().getUser().getUserRole().equals("User")) {
			reservation.setVisible(true);
		}
		if (Mediator.getInstance().getUser().getUserRole().equals("Cashier")) {
			Mediator.getInstance().setSelectedEvent(selectedEvent);
			Mediator.getInstance().getGeneratedTicket().setVisible(false);
			setTicket (Mediator.getInstance().getTicketGenerator());
			Mediator.getInstance().getTicketGenerator().setVisible(true);
		}
	}
	
	@FXML 
	private void makeReservation (ActionEvent event) {
		try {
			ReservationS rS = new ReservationS ();
			Reservation reservation = setReservation ();
			EventService eventS = new EventService ();
			selectedEvent.setPlaces(selectedEvent.getPlaces()- Integer.parseInt(quantity.getText()));
			rS.add(reservation);
			eventS.update(selectedEvent);
			message.setText("You made a reservation");
		} catch (Exception e) {
			message.setText(e.getMessage());
		}
	}
}
