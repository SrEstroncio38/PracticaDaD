package es.urjc.TicTakTicket.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Users {
	
	@Id
	private String username;
	
	private String password;
	
	protected Users() {}
	
	public Users(String username, String password) {
		this.username = username;
		this.password = password;
	}

}
