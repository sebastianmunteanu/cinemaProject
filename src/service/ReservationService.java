package service;

import java.util.List;

import javax.persistence.Persistence;

import dao.ReservationDao;
import model.Movieevent;
import model.Reservation;
import model.User;

public class ReservationService {
	private ReservationDao reservationDao;
	
	public ReservationService() {
		try {
			reservationDao = new ReservationDao(Persistence.createEntityManagerFactory("Cinema"));
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
	
	public void addReservation(Reservation newReservation) {
		reservationDao.create(newReservation);
	}
	
	public void deleteReservation(Reservation deleteReservation) throws Exception {
		if (deleteReservation == null) {
			throw new Exception("You don't select a reservetion");
		}
		reservationDao.remove(deleteReservation, deleteReservation.getReservationId());
	}
	
	public List<Reservation> findMyReservations(User user) throws Exception {
		List<Reservation> reservations = reservationDao.findMyReservation(user);
		if (reservations.size() == 0) {
			throw new Exception("No reservations");
		}
		return reservations;
	}	
	
	public Reservation findReservation(String reservationId) throws Exception {
		Reservation reservation = reservationDao.findReservation(reservationId);
		if (reservation.equals(null)) {
			throw new Exception("No reservation");
		}
		return reservation;
	}
	public List<Reservation> findEventReservations(Movieevent movieEvent) {
		List<Reservation> reservations = reservationDao.findReservationForEvent(movieEvent);
		return reservations;
	}
}
