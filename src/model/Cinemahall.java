package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cinemahalls database table.
 * 
 */
@Entity
@Table(name="cinemahalls")
@NamedQuery(name="Cinemahall.findAll", query="SELECT c FROM Cinemahall c")
public class Cinemahall implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int hallId;

	private int numberOfPlaces;

	//bi-directional many-to-one association to Movieevent
	@OneToMany(mappedBy="cinemahall")
	private List<Movieevent> movieevents;

	public Cinemahall() {
	}

	public int getHallId() {
		return this.hallId;
	}

	public void setHallId(int hallId) {
		this.hallId = hallId;
	}

	public int getNumberOfPlaces() {
		return this.numberOfPlaces;
	}

	public void setNumberOfPlaces(int numberOfPlaces) {
		this.numberOfPlaces = numberOfPlaces;
	}

	public List<Movieevent> getMovieevents() {
		return this.movieevents;
	}

	public void setMovieevents(List<Movieevent> movieevents) {
		this.movieevents = movieevents;
	}

	public Movieevent addMovieevent(Movieevent movieevent) {
		getMovieevents().add(movieevent);
		movieevent.setCinemahall(this);

		return movieevent;
	}

	public Movieevent removeMovieevent(Movieevent movieevent) {
		getMovieevents().remove(movieevent);
		movieevent.setCinemahall(null);
		return movieevent;
	}

	@Override
	public String toString() {
		return "Hall number: " + hallId + ", Places=" + numberOfPlaces;
	}
	

}