package es.urjc.ServicioInterno.Entities;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket,Integer>{
	
	List<Ticket> findByEvent (Event event);
	List<Ticket> findByPrice (int price);
}
