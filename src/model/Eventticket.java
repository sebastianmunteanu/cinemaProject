package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eventticket database table.
 * 
 */
@Entity
@NamedQuery(name="Eventticket.findAll", query="SELECT e FROM Eventticket e")
public class Eventticket implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int eventticketId;

	private Date aquisitionDate;

	private int quantity;

	private float totalPrice;

	//bi-directional many-to-one association to Movieevent
	@ManyToOne
	@JoinColumn(name="eventId")
	private Movieevent movieevent;

	public Eventticket() {
	}

	public int getEventticketId() {
		return this.eventticketId;
	}

	public void setEventticketId(int eventticketId) {
		this.eventticketId = eventticketId;
	}

	@Override
	public String toString() {
		return "Eventticket [eventticketId=" + eventticketId + ", aquisitionDate=" + aquisitionDate + ", quantity="
				+ quantity + ", totalPrice=" + totalPrice + ", movieevent=" + movieevent + "]";
	}

	public Date getAquisitionDate() {
		return this.aquisitionDate;
	}

	public void setAquisitionDate(Date aquisitionDate) {
		this.aquisitionDate = aquisitionDate;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Movieevent getMovieevent() {
		return this.movieevent;
	}

	public void setMovieevent(Movieevent movieevent) {
		this.movieevent = movieevent;
	}

}