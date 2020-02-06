package es.urjc.TicTakTicket.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Events {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToOne
	private Users user;
	
	private String name;
	
	protected Events() {}
	
	public Events(Users user, String name) {
		this.user = user;
		this.name = name;
	}

}
