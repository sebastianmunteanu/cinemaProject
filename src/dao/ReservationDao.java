package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import model.Movieevent;
import model.Reservation;
import model.User;

public class ReservationDao extends GenericDao<Reservation>{

	private EntityManagerFactory factory;
	
	
	public ReservationDao(EntityManagerFactory factory) {
		super(Reservation.class);
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
	
	public List <Reservation> findMyReservation (User user) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery <Reservation> q = cb.createQuery(Reservation.class);
		Root<Reservation> c = q.from(Reservation.class);
		ParameterExpression<User> paramName = cb.parameter(User.class);
		q.select(c).where(cb.equal(c.get("user"), paramName));
		TypedQuery<Reservation> query = em.createQuery(q);
		query.setParameter(paramName, user);
		List<Reservation> results = query.getResultList();
		return results;
	}
	
	public Reservation findReservation (String cod) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery <Reservation> q = cb.createQuery(Reservation.class);
		Root<Reservation> c = q.from(Reservation.class);
		ParameterExpression<String> paramName = cb.parameter(String.class);
		q.select(c).where(cb.equal(c.get("reservationNo"), paramName));
		TypedQuery<Reservation> query = em.createQuery(q);
		query.setParameter(paramName, cod);
		Reservation result = query.getSingleResult();
		return result;
	}
	
	public List <Reservation> findReservationForEvent (Movieevent movieEvent) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery <Reservation> q = cb.createQuery(Reservation.class);
		Root<Reservation> c = q.from(Reservation.class);
		ParameterExpression<Movieevent> paramName = cb.parameter(Movieevent.class);
		q.select(c).where(cb.equal(c.get("movieevent"), paramName));
		TypedQuery<Reservation> query = em.createQuery(q);
		query.setParameter(paramName, movieEvent);
		List<Reservation> results = query.getResultList();
		return results;
	}
	
}
