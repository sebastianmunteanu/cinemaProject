package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import model.Movie;
import model.Movieevent;

public class EventDao extends GenericDao<Movieevent>{
	
	private EntityManagerFactory factory;
	
	public EventDao(EntityManagerFactory factory) {
		super(Movieevent.class);
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
	
	public List <Movieevent> findAllEventsForMovie (Movie movie) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery <Movieevent> q = cb.createQuery(Movieevent.class);
		Root<Movieevent> c = q.from(Movieevent.class);
		ParameterExpression<Movie> paramName = cb.parameter(Movie.class);
		q.select(c).where(cb.equal(c.get("movie"), paramName));
		TypedQuery<Movieevent> query = em.createQuery(q);
		query.setParameter(paramName, movie);
		List<Movieevent> results = query.getResultList();
		return results;
	}
	
	
}
