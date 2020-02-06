package es.urjc.TicTakTicket.Entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	private User user;
	
	private String ownerName;
	private String cardNumber;
	private Date expireDate;
	private short cvv;
	
	protected Payment() {}
	
	public Payment(User user, String ownerName, String cardNumber, Date expireDate, short cvv) {
		this.user = user;
		this.ownerName = ownerName;
		this.cardNumber = cardNumber;
		this.expireDate = expireDate;
		this.cvv = cvv;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public short getCvv() {
		return cvv;
	}

	public void setCvv(short cvv) {
		this.cvv = cvv;
	}

}
