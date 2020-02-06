package es.urjc.TicTakTicket.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
	
	@Id
	private String username;
	
	private String password;
	
	protected User() {}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

}
