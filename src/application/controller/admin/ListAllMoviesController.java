package application.controller.admin;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Cinemahall;
import model.Eventticket;
import model.Movie;
import model.Movieevent;
import model.Reservation;
import service.CinemaHallService;
import service.EventService;
import service.EventTicketS;
import service.MovieService;
import service.ReservationService;
import service.ReservationTicketS;
import service.generic.Mediator;

public class ListAllMoviesController implements Initializable {
	String styleSheet = "/application/style/admin/ListAllMovies.css";
	String formDisplay = "/application/style/admin/FormDisplay.css";
	String tabelStyle = "/application/style/tabelStyle.css";
	
	Movie selectedMovie = new Movie ();
	private float directBuy;
	private float incomes;
	private float reservationBuy;
	
	@FXML
	private TableView <Movie> tabelView;
	
	@FXML 
	private TableColumn <Movie, Integer> idColumn;
	
	@FXML 
	private TableColumn <Movie, String>  nameColumn;
	
	@FXML 
	private TableColumn <Movie, String> durationColumn;
	
	@FXML 
	private TableColumn <Movie, Float> ratingColumn;
	
	@FXML
	private ComboBox<Cinemahall> choseHall;
	
	@FXML
	private ComboBox<String> choseHour;
	
	@FXML
	private AnchorPane createEvent;
	
	@FXML
	private AnchorPane root;
	
	@FXML
	private TextField eventPrice;
	
	@FXML
	private Label selectedMovieForEvent;
	
	@FXML
	private Label errorMessage;
	
	@FXML
	private DatePicker date;
	
	private void loadDate () {
		idColumn.setCellValueFactory(new PropertyValueFactory <> ("movieId"));
		nameColumn.setCellValueFactory(new PropertyValueFactory <> ("movieName"));
		durationColumn.setCellValueFactory(new PropertyValueFactory <> ("runningTime"));
		ratingColumn.setCellValueFactory(new PropertyValueFactory <> ("rating"));
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		CinemaHallService hallS = new CinemaHallService ();
		choseHall.getItems().setAll(FXCollections.observableList(hallS.getAllMovies()));
		choseHour.getItems().setAll("16:00", "17:00", "18:00", "19:00","20:00", "21:00");
		root.getStylesheets().add(styleSheet);
		createEvent.setVisible(false);
		MovieService movieS = new MovieService ();
		ObservableList<Movie> moviesList = FXCollections.observableList(movieS.getAllMovies());
		loadDate();
		tabelView.setItems(moviesList);
		tabelView.getStylesheets().add(tabelStyle);
	}
	
	private void printMovieInfo () {
		AnchorPane root = Mediator.getInstance().getMoveInfo();
		root.getStylesheets().add(formDisplay);
		Label movieName = (Label) root.getChildren().get(1);
		directBuy = 0;
		incomes = 0;
		reservationBuy = 0;
		movieName.setText(selectedMovie.getMovieName());
		EventService eventS = new EventService ();
		ObservableList<Movieevent> eventList = null;
		try {
			eventList = FXCollections.observableList(eventS.getEventForMovie(selectedMovie));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		Label totalEvents = (Label) root.getChildren().get(2);
		totalEvents.setText(String.valueOf(eventList.size()));
		root.setVisible(true);
		Label totalIncome = (Label) root.getChildren().get(6);
		Label directBuyLabel = (Label) root.getChildren().get(8);
		Label reservationBuyLabel = (Label) root.getChildren().get(10);
		eventList.forEach (event -> {
			EventTicketS ticketS = new EventTicketS ();
			ObservableList<Eventticket> ticketslist = FXCollections.observableList (ticketS.getEventForMovie(event));
			ticketslist.forEach(ticket -> {
				directBuy += ticket.getTotalPrice();
			});
		});
		eventList.forEach (event -> {
			ReservationService reservationS = new ReservationService ();
			ObservableList<Reservation> reservations = FXCollections.observableList (reservationS.findEventReservations(event));
			reservations.forEach(reservation -> {
				ReservationTicketS reservationTicketS = new ReservationTicketS();
				if (!Objects.isNull(reservationTicketS.find(reservation.getReservationId()))) {
					reservationBuy += reservation.getTotalPrice();
				}
			});
		});
		incomes = directBuy + reservationBuy;
		directBuyLabel.setText(String.valueOf(directBuy));
		reservationBuyLabel.setText(String.valueOf(reservationBuy));
		totalIncome.setText(String.valueOf(incomes));
	}
	
	private void resetValuesForEvent () {
		choseHall.setValue(null);
		eventPrice.setText("");
		choseHour.setValue("");
		date.setValue(null);
	}

	@FXML 
	private void getItem (MouseEvent event) {
		selectedMovie = tabelView.getSelectionModel().getSelectedItem();
		selectedMovieForEvent.setText(selectedMovie.getMovieName());
		createEvent.setVisible(true);
		printMovieInfo ();
		resetValuesForEvent ();
		
	}
	
	@FXML 
	private void createEvent (ActionEvent event) {
		EventService eventS = new EventService ();
		
		try {
			Movieevent mEvent = new Movieevent ();
			mEvent.setMovie(selectedMovie);
			mEvent.setInsertDate(new Date());
			if (Objects.isNull(choseHall.getValue())) {
				throw new NullPointerException ("Select a hall");
			}
			mEvent.setCinemahall(choseHall.getValue());
			if (eventPrice.getText().equals("")) {
				throw new Exception ("Set a price");
			}
			mEvent.setPrice(Float.parseFloat(eventPrice.getText()));
			if (Objects.isNull(choseHour.getValue()) || Objects.isNull(date.getValue())) {
				throw new NullPointerException ("Select date and time");
			}
			mEvent.setEventDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date.getValue() + " " + choseHour.getValue() + ":00"));
			mEvent.setPlaces(choseHall.getValue().getNumberOfPlaces());
			eventS.addEvent(mEvent);
			errorMessage.setText("Event inserted");
		} catch (Exception e) {
			errorMessage.setText(e.getMessage());
		}
	}
}
