package es.urjc.TicTakTicket.Controllers;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.urjc.TicTakTicket.Entities.Event;
import es.urjc.TicTakTicket.Entities.EventRepository;
import es.urjc.TicTakTicket.Entities.Ticket;
import es.urjc.TicTakTicket.Entities.TicketRepository;
import es.urjc.TicTakTicket.Entities.User;
import es.urjc.TicTakTicket.Entities.UserRepository;

@Controller
public class CreateEventController {
	
	@Autowired
	private EventRepository eventR;
	
	@Autowired
	private UserRepository userR;
	
	@Autowired
	private TicketRepository ticketR;
	
	User eventUser;
	
	@PostConstruct
	public void init() {
		User a = new User("default", "1234");
		userR.save(a);
		User b = new User("Ramon", "1234");
		userR.save(b);
	}

	
	
	@RequestMapping(value = {"/createEvent","/createEvent/{username}"})
	public String Load(Model model, @PathVariable(required = false) String username) {
		if (username != null) {
			Optional<User> user = userR.findById(username);
			if (user.isPresent()) {
				eventUser = user.get();
			} 
		} else {
			eventUser = userR.findById("default").get();
		}
		
		model.addAttribute("page_title", "Crear Evento");
		
		return "createEvent_template";
	}
	
	@PostMapping("/createEvent")
	public String Submit(Model model, @RequestParam String eventName, @RequestParam String eventDesc, 
			@RequestParam String ticketName,  @RequestParam String ticketPrice, @RequestParam String ticketDesc) {
		
		if(eventName != null && eventDesc != null && ticketName != null && ticketDesc != null && ticketPrice != null && eventUser != null) {
			
			
			Event event = new Event(eventUser, eventName, eventDesc);
			
			float price = Float.parseFloat(ticketPrice);
			Ticket ticket = new Ticket(price, ticketName, ticketDesc, event);
			eventR.save(event);
			ticketR.save(ticket);
			
			event.addTicket(ticket);
			eventR.save(event);
			
			return "redirect:/";
		} else {
			
			return "redirect:/createEvent";
			
		}
		
		}
	
	
	
}