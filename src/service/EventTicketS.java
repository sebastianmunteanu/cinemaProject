package service;

import java.util.List;

import javax.persistence.Persistence;

import dao.EventTicketDao;
import model.Eventticket;
import model.Movieevent;

public class EventTicketS extends GenericService <Eventticket, EventTicketDao>{
	
	private EventTicketDao eventTicketDao;
	
	public EventTicketS() {
		super(Eventticket.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public EventTicketDao getDao() {
		try {
			eventTicketDao = new EventTicketDao(Persistence.createEntityManagerFactory("Cinema"));
			return eventTicketDao;
		} catch (Exception ex) {
			System.out.println(ex);
			return null;
		}
	}
	
	public List <Eventticket> getEventForMovie (Movieevent movieevent) {
		return eventTicketDao.findAllTickets(movieevent);
	}

}
