package service;

import javax.persistence.Persistence;

import dao.ReservationDao;
import model.Reservation;

public class ReservationS extends GenericService <Reservation, ReservationDao> {
	
	private ReservationDao reservationDao;
	
	public ReservationS() {
		super(Reservation.class);
	}

	@Override
	public ReservationDao getDao() {
		try {
			reservationDao = new ReservationDao(Persistence.createEntityManagerFactory("Cinema"));
			return reservationDao;
		} catch (Exception ex) {
			System.out.println(ex);
			return null;
		}
	}
}
