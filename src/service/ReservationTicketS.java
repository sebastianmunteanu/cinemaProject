package service;

import javax.persistence.Persistence;

import dao.ReservationTicketDao;
import model.Reservationticket;

public class ReservationTicketS extends GenericService <Reservationticket, ReservationTicketDao> {
	
	public ReservationTicketS() {
		super(Reservationticket.class);
	}

	private ReservationTicketDao reservationTicketDao;
	@Override
	public ReservationTicketDao getDao() {
		try {
			reservationTicketDao = new ReservationTicketDao(Persistence.createEntityManagerFactory("Cinema"));
			return reservationTicketDao;
		} catch (Exception ex) {
			System.out.println(ex);
			return null;
		}
	}
}
