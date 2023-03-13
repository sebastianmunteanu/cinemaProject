package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import model.Eventticket;
import model.Movieevent;

public class EventTicketDao extends GenericDao<Eventticket> {
	private EntityManagerFactory factory;
	
	public EventTicketDao(EntityManagerFactory factory) {
		super(Eventticket.class);
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
	
	public List <Eventticket> findAllTickets (Movieevent movieEvent) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery <Eventticket> q = cb.createQuery(Eventticket.class);
		Root<Eventticket> c = q.from(Eventticket.class);
		ParameterExpression<Movieevent> paramName = cb.parameter(Movieevent.class);
		q.select(c).where(cb.equal(c.get("movieevent"), paramName));
		TypedQuery<Eventticket> query = em.createQuery(q);
		query.setParameter(paramName, movieEvent);
		List<Eventticket> results = query.getResultList();
		return results;
	}
}
