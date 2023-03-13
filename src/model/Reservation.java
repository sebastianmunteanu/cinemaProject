package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;



@Entity
@Table(name = "reservations")
@NamedQuery(name = "Reservation.findAll", query = "SELECT r FROM Reservation r")
public class Reservation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int reservationId;

	private int quantity;

	@Temporal(TemporalType.TIMESTAMP)
	private Date reservationDate;

	private String reservationNo;

	private float totalPrice;

	// bi-directional many-to-one association to Movieevent
	@ManyToOne
	@JoinColumn(name = "eventId")
	private Movieevent movieevent;

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "userCod")
	private User user;

	public Reservation() {
	}

	public int getReservationId() {
		return this.reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getReservationDate() {
		return this.reservationDate;
	}

	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}

	public String getReservationNo() {
		return this.reservationNo;
	}

	public void setReservationNo(String reservationNo) {
		this.reservationNo = reservationNo;
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

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Reservation [reservationId=" + reservationId + ", quantity=" + quantity + ", reservationDate="
				+ reservationDate + ", reservationNo=" + reservationNo + ", totalPrice=" + totalPrice + ", movieevent="
				+ movieevent + ", user=" + user + "]";
	}
}