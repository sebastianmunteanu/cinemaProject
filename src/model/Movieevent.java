package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name="movieevents")
@NamedQuery(name="Movieevent.findAll", query="SELECT m FROM Movieevent m")
public class Movieevent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int eventId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date eventDate;

	//bi-directional many-to-one association to Cinemahall
	@ManyToOne
	@JoinColumn(name="hallId")
	private Cinemahall cinemahall;

	@Temporal(TemporalType.TIMESTAMP)
	private Date insertDate;

	//bi-directional many-to-one association to Movie
	@ManyToOne
	@JoinColumn(name="movieId")
	private Movie movie;


	private int places;

	private float price;

	//bi-directional many-to-one association to Eventticket
	@OneToMany(mappedBy="movieevent")
	private List<Eventticket> eventtickets;

	public Movieevent() {
	}

	public int getEventId() {
		return this.eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public Date getEventDate() {
		return this.eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public Date getInsertDate() {
		return this.insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public int getPlaces() {
		return this.places;
	}

	public void setPlaces(int places) {
		this.places = places;
	}

	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public List<Eventticket> getEventtickets() {
		return this.eventtickets;
	}

	public void setEventtickets(List<Eventticket> eventtickets) {
		this.eventtickets = eventtickets;
	}
	
	public Cinemahall getCinemahall() {
		return this.cinemahall;
	}

	public void setCinemahall(Cinemahall cinemahall) {
		this.cinemahall = cinemahall;
	}

	public Movie getMovie() {
		return this.movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

}