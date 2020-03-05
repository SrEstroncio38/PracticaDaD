package es.urjc.ServicioInterno.Entities;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer>{
	
	List<Order> findByUser(User user);
	List<Order> findByTickets(Ticket ticket);
	Page<Order> findByUser(User user, Pageable page);
}
