package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import model.Cinemahall;

public class HallDao extends GenericDao<Cinemahall> {
private EntityManagerFactory factory;
	
	public HallDao(EntityManagerFactory factory) {
		super(Cinemahall.class);
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
