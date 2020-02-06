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
	
	private String owner_name;
	private String card_number;
	private Date expire_date;
	private short cvv;
	
	protected Payment() {}
	
	public Payment(User user, String owner_name, String card_number, Date expire_date, short cvv) {
		this.user = user;
		this.owner_name = owner_name;
		this.card_number = card_number;
		this.expire_date = expire_date;
		this.cvv = cvv;
	}

}
