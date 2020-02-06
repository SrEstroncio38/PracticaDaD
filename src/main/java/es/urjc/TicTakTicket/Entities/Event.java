package es.urjc.TicTakTicket.Entities;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;



@Entity
public class Event {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	private User user;
	
	private String name;
	
	@OneToMany
	private ArrayList<Ticket> tickets = new ArrayList<Ticket>();
	
	protected Event() {}
	
	public Event(User user, String name) {
		this.user = user;
		this.name = name;
	}

}
