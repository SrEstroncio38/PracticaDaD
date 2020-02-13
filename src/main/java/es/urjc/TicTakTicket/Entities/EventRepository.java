package es.urjc.TicTakTicket.Entities;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Integer>{

	List<Event> findByUser(User user);
	List<Event> findByName(String name);
}
