package es.urjc.TicTakTicket.Entities;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@CacheConfig(cacheNames="eventos")
public interface EventRepository extends JpaRepository<Event,Integer>{
	
	//@CacheEvict(allEntries=true)
	Event save(Event event);

	//@Cacheable
	List<Event> findByUser(User user);
	//@Cacheable
	List<Event> findByName(String name);
	//@Cacheable
	Page<Event> findByUser(User user, Pageable page);
	
	//@Cacheable
	List<Event> findAll();
	
	//@Cacheable
	Page<Event> findAll(Pageable page);
}
