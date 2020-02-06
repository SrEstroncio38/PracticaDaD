package es.urjc.TicTakTicket.Entities;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
	
	@Id
	private String username;
	
	private String password;
	
	@OneToMany
	private ArrayList<Order> orders = new ArrayList<>();
	
	protected User() {}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

}
