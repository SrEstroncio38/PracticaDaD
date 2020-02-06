package es.urjc.TicTakTicket.Entities;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private float totalPrice;
	private ArrayList<String> names = new ArrayList<>();
	
	@OneToMany
	private ArrayList<Ticket> tickets = new ArrayList<>();
	
	@ManyToOne
	private User user;
	
	private ArrayList<Integer> sits = new ArrayList<>();
	
	protected Order() {}
	
	public Order(String[] names, ArrayList<Ticket> tickets, User user, int[] sits) {
		this.user = user;
		for (Ticket t : tickets) {
			this.tickets.add(t);
			this.totalPrice += t.getPrice();
		}
		for (int i = 0; i < names.length; i++) {
			this.names.add(names[i]);
		}
		for (int i = 0; i < sits.length; i++) {
			this.sits.add(sits[i]);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public ArrayList<String> getNames() {
		return names;
	}

	public void setNames(ArrayList<String> names) {
		this.names = names;
	}

	public ArrayList<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(ArrayList<Ticket> tickets) {
		this.tickets = tickets;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ArrayList<Integer> getSits() {
		return sits;
	}

	public void setSits(ArrayList<Integer> sits) {
		this.sits = sits;
	}
}
