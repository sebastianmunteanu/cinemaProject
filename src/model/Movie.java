package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the movies database table.
 * 
 */
@Entity
@Table(name="movies")
@NamedQuery(name="Movie.findAll", query="SELECT m FROM Movie m")
public class Movie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int movieId;

	private String movieName;

	private float rating;

	private int runningTime;

	//bi-directional many-to-one association to Movieevent
	@OneToMany(mappedBy="movie")
	private List<Movieevent> movieevents;

	public Movie() {
	}

	public int getMovieId() {
		return this.movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getMovieName() {
		return this.movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public float getRating() {
		return this.rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public int getRunningTime() {
		return this.runningTime;
	}

	public void setRunningTime(int runningTime) {
		this.runningTime = runningTime;
	}

	public List<Movieevent> getMovieevents() {
		return this.movieevents;
	}

	public void setMovieevents(List<Movieevent> movieevents) {
		this.movieevents = movieevents;
	}

	public Movieevent addMovieevent(Movieevent movieevent) {
		getMovieevents().add(movieevent);
		movieevent.setMovie(this);
		return movieevent;
	}

	public Movieevent removeMovieevent(Movieevent movieevent) {
		getMovieevents().remove(movieevent);
		movieevent.setMovie(null);
		return movieevent;
	}

	@Override
	public String toString() {
		return "Movie [movieId=" + movieId + ", movieName=" + movieName + ", rating=" + rating + ", runningTime="
				+ runningTime + "]";
	}
}