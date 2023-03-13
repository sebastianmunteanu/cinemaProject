package service.generic;

import javafx.scene.layout.AnchorPane;
import model.Movieevent;
import model.User;

public class Mediator {
	private static Mediator INSTANCE;
	private User user;
	private AnchorPane ticketGenerator;
	private AnchorPane generatedTicket;
	private AnchorPane moveInfo;
	
	public AnchorPane getMoveInfo() {
		return moveInfo;
	}

	public void setMoveInfo(AnchorPane moveInfo) {
		this.moveInfo = moveInfo;
	}

	public AnchorPane getGeneratedTicket() {
		return generatedTicket;
	}

	public void setGeneratedTicket(AnchorPane generatedTicket) {
		this.generatedTicket = generatedTicket;
	}

	private Movieevent selectedEvent;
	
    public Movieevent getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(Movieevent selectedEvent) {
		this.selectedEvent = selectedEvent;
	}

	public AnchorPane getTicketGenerator() {
		return ticketGenerator;
	}

	public void setTicketGenerator(AnchorPane ticketGenerator) {
		this.ticketGenerator = ticketGenerator;
	}

	public static Mediator getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Mediator();
        }
        return INSTANCE;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public User getUser() {
        return user;
    }
}
