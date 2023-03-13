package application.controller;

import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Eventticket;
import model.Movieevent;
import model.Reservation;
import model.Reservationticket;
import service.EventService;
import service.EventTicketS;
import service.ReservationService;
import service.ReservationTicketS;
import service.generic.LogOut;
import service.generic.Mediator;

public class CashierController implements Initializable {
	String buttonStyle = "/application/style/ButtonLayout.css";
	String generateTicketStyle = "/application/style/cashier/TicketGenerator.css";
	private String eventsL = "/application/layout/admin/ListAllEvents.fxml";
	private Reservation reservation;
	Reservationticket reservationTicket = new Reservationticket();

	@FXML
	private AnchorPane root;

	@FXML
	private Label quantity;

	@FXML
	private TextField inputQuantity;

	@FXML
	private Label clientName;

	@FXML
	private Label price;

	@FXML
	private Label eventName;

	@FXML
	private Label ticketMessage;

	@FXML
	private TextField reservationId;

	@FXML
	private Button logOut;

	@FXML
	private Button generateTicket;

	@FXML
	private AnchorPane ticketGenerator;

	@FXML
	private AnchorPane generatedTicket;

	@FXML
	private AnchorPane tabelView;

	@FXML
	void generateTicket(ActionEvent event) {
	}

	@FXML
	private Button search;
	
	private void setTicketsIfOk () {
		ticketGenerator.setVisible(false);
		ticketMessage.setText("Ticket was generated");
		generatedTicket.setVisible(true);
	}
	
	private void setTicketsIfNotOk (String message) {
		ticketMessage.setText(message);
		generatedTicket.setVisible(true);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		root.getStylesheets().add(buttonStyle);
		Mediator.getInstance().setTicketGenerator(ticketGenerator);
		Mediator.getInstance().setGeneratedTicket(generatedTicket);
		ticketGenerator.setVisible(false);
		generatedTicket.setVisible(false);
		try {
			Parent insertLayout = (Parent) FXMLLoader.load(getClass().getResource(eventsL));
			tabelView.getChildren().setAll(insertLayout);
		} catch (Exception e) {
			System.out.println(e);
		}
		ticketGenerator.getStylesheets().add(generateTicketStyle);
		search.setOnAction((event) -> {
			ticketGenerator.setVisible(false);
			ReservationService reservationS = new ReservationService();
			try {
				reservation = reservationS.findReservation(reservationId.getText());
				if (Objects.isNull(reservation)) {
					throw new Exception ("No reservation with this code");
				}
				eventName.setText(reservation.getMovieevent().getMovie().getMovieName());
				clientName.setText(reservation.getUser().getFirstName() + " " + reservation.getUser().getLastName());
				price.setText((String.valueOf(reservation.getTotalPrice())));
				quantity.setText((String.valueOf(reservation.getQuantity())));
				ticketGenerator.setVisible(true);
				generatedTicket.setVisible(false);
			} catch (Exception e) {
				setTicketsIfNotOk (e.getMessage());
			}
		});
		logOut.setOnAction((event) -> {
			LogOut<AnchorPane> logOut = new LogOut<>();
			logOut.logOut(root);
		});
		generateTicket.setOnAction((event) -> {
			EventTicketS eventTicketS = new EventTicketS();
			if (clientName.getText() == "") {
				try {
					Eventticket eventTicket = new Eventticket();
					eventTicket.setAquisitionDate(new Date());
					eventTicket.setMovieevent(Mediator.getInstance().getSelectedEvent());
					if (Integer.parseInt(inputQuantity.getText()) <= 0) {
						throw new Exception("Quantity can't \n be null or negative");
					} else {
						eventTicket.setQuantity(Integer.parseInt(inputQuantity.getText()));
					}
					if (Float.parseFloat(price.getText()) <= 0.0) {
						throw new Exception("Insert quantity again.");
					} else {
						eventTicket.setTotalPrice(Float.parseFloat(price.getText()));
					}
					// update event places
					Movieevent eventToModify = Mediator.getInstance().getSelectedEvent();
					eventToModify.setPlaces(eventToModify.getPlaces() - Integer.parseInt(inputQuantity.getText()));
					// check negative value after quantity request
					if (eventToModify.getPlaces() < 0) {
						throw new Exception("Insert another quantity");
					} else {
						EventService eventService = new EventService();
						eventService.update(eventToModify);
					}
					eventTicketS.add(eventTicket);
					setTicketsIfOk ();
				} catch (Exception e) {
					setTicketsIfNotOk (e.getMessage());
				}
			} else {
				ReservationTicketS reservationTicketS = new ReservationTicketS();
				try {
					if (!Objects.isNull(reservationTicketS.find(reservation.getReservationId()))) {
						throw new Exception ("Reservetion is already confirmed");
					}
					reservationTicket.setReservation(reservation);
					reservationTicket.setAquisitionDate(new Date());
					reservationTicketS.add(reservationTicket); 
					setTicketsIfOk ();
				} catch (Exception e) {
					setTicketsIfNotOk (e.getMessage());
				}
			}
		});
	}
}
