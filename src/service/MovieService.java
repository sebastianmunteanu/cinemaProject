package service;

import java.util.List;

import javax.persistence.Persistence;

import dao.MovieDao;
import model.Movie;

public class MovieService {
	private MovieDao movieDao;

	public MovieService() {
		try {
			movieDao = new MovieDao(Persistence.createEntityManagerFactory("Cinema"));
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void addMovie(Movie newMovie) {
		movieDao.create(newMovie);
	}
	
	public void removeMovie(int movieId) throws Exception {
		movieDao.remove(new Movie (), movieId);
	}

	public void updateMovie(Movie updatedMovie) {
		movieDao.update(updatedMovie);
	}

	public List<Movie> getAllMovies() {
		return movieDao.findAll();
	}
	public Movie find (int id) {
		return movieDao.find(id);
	}
}
