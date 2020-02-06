package es.urjc.TicTakTicket.Entities;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer>{
	
	List<Order> findByUser(User user);
	List<Order> findByTicket(Ticket ticket);
	List<Order> findByNumTickets(int numTickets);
}
