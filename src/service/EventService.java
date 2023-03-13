package service;

import java.util.List;

import javax.persistence.Persistence;

import dao.EventDao;
import model.Movie;
import model.Movieevent;

public class EventService {
	private EventDao eventDao;
	
	public EventService() {
		try {
			eventDao = new EventDao(Persistence.createEntityManagerFactory("Cinema"));
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
	
	public void addEvent(Movieevent newEvent) {
		eventDao.create(newEvent);
	}
	
	public void update(Movieevent newEvent) {
		eventDao.update(newEvent);
	}
	
	public List<Movieevent> getAllEvents (){
		return eventDao.findAll();
	}
	
	public List <Movieevent> getEventForMovie (Movie movie) {
		return eventDao.findAllEventsForMovie(movie);
	}
}
