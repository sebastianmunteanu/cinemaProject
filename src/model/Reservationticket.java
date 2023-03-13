package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the reservationtickets database table.
 * 
 */
@Entity
@Table(name="reservationtickets")
@NamedQuery(name="Reservationticket.findAll", query="SELECT r FROM Reservationticket r")
public class Reservationticket implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int ticketId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date aquisitionDate;
	
	@OneToOne (fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn (name = "ticketId")
	private Reservation reservation;

	public Reservationticket() {
	}

	public int getTicketId() {
		return this.ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public Date getAquisitionDate() {
		return this.aquisitionDate;
	}

	public void setAquisitionDate(Date aquisitionDate) {
		this.aquisitionDate = aquisitionDate;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	@Override
	public String toString() {
		return "Reservationticket [ticketId=" + ticketId + ", aquisitionDate=" + aquisitionDate + ", reservation="
				+ reservation + "]";
	}

}