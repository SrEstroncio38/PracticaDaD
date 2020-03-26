package es.urjc.TicTakTicket.Entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class User {
	
	@Id
	private String username;
	
	private String passwordHash;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles = new ArrayList<String>();
	
	protected User() {}
	
	public User(String username, String password) {
		this.username = username;
		this.passwordHash = new BCryptPasswordEncoder().encode(password);
		roles.add("ROLE_USER");
	}
	
	public User(String username, String password, String role) {
		this.username = username;
		this.passwordHash = new BCryptPasswordEncoder().encode(password);
		roles.add(role);
	}
	
	public User(String username, String password, String[] roles) {
		this.username = username;
		this.passwordHash = new BCryptPasswordEncoder().encode(password);
		for(String role: roles) {
			this.roles.add(role);
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String password) {
		this.passwordHash = new BCryptPasswordEncoder().encode(password);
	}
	
	public List<String> getRoles() {
		return roles;
	}
	
	public void setRoles(String[] roles) {
		for(String role: roles) {
			this.roles.add(role);
		}
	}

}
