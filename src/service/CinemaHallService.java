package service;

import java.util.List;

import javax.persistence.Persistence;

import dao.HallDao;
import model.Cinemahall;

public class CinemaHallService {
	private HallDao hallDao;
	
	public CinemaHallService() {
		try {
			hallDao = new HallDao(Persistence.createEntityManagerFactory("Cinema"));
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
	
	public List<Cinemahall> getAllMovies() {
		return hallDao.findAll();
	}
}
