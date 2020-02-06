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

}
