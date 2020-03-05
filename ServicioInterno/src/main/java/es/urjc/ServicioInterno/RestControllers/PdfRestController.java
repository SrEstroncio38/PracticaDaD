package es.urjc.ServicioInterno.RestControllers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import es.urjc.ServicioInterno.Entities.*;

@RestController
public class PdfRestController {
	
	private Map<Long, User> anuncios = new ConcurrentHashMap<>();
	private AtomicLong lastId = new AtomicLong();
	
	@PostMapping("/pdf/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public User userPrueba(@RequestBody User user) {
		
		return user;
	}
	
}
