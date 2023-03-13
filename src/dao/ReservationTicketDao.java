package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import model.Reservationticket;

public class ReservationTicketDao extends GenericDao<Reservationticket> {

	private EntityManagerFactory factory;
	
	public ReservationTicketDao(EntityManagerFactory factory) {
		super(Reservationticket.class);
		this.factory = factory;
	}

	
	
	@Override
	public EntityManager getEntityManager() {
		try {
			return factory.createEntityManager();
		} catch (Exception ex) {
			System.out.println("The entity manager cannot be created!");
			return null;
		}
	}

}
