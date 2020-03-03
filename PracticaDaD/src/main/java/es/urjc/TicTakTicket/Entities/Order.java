package es.urjc.TicTakTicket.Entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_table")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String displayNumber;
	
	private float totalPrice;
	
	@ManyToMany
	private List<Ticket> tickets = new ArrayList<>();
	
	private String person1;
	private String person2;
	private String person3;
	private String person4;
	private String person5;
	
	@ManyToOne
	private User user;
	
	protected Order() {}
	
	public Order(List<String> names, List<Ticket> tickets, User user, String number) {
		this.user = user;
		this.displayNumber = number;
		for (Ticket t : tickets) {
			this.tickets.add(t);
			this.totalPrice += t.getPrice();
		}
		setNames(names);
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

	public List<String> getNames() {
		List<String> names = new ArrayList<String>();
		names.add(person1);
		names.add(person2);
		names.add(person3);
		names.add(person4);
		names.add(person5);
		return names;
	}

	public void setNames(List<String> names) {
		int idx = names.size();
		if (idx > 0)
			person1 = names.get(0);
		if (idx > 1)
			person2 = names.get(1);
		if (idx > 2)
			person3 = names.get(2);
		if (idx > 3)
			person4 = names.get(3);
		if (idx > 4)
			person5 = names.get(4);
	}

	public List<Ticket> getTickets() {
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
}
