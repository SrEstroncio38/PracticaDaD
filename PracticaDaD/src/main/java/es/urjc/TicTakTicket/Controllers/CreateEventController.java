package es.urjc.TicTakTicket.Controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	
	@RequestMapping(value = {"/createEvent"})
	public String Load(Model model, HttpServletRequest request) {
		
		Principal currentUser = request.getUserPrincipal();
		if (currentUser != null)
			model.addAttribute("loggedUser", currentUser.getName());
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		model.addAttribute("token", token.getToken());
		
		try {
			eventUser = userR.findById(currentUser.getName()).get();
		} catch (Exception e) {
			return "redirect:/dbError";
		}
		
		model.addAttribute("page_title", "Crear Evento");
		
		return "createEvent_template";
	}
	
	@PostMapping("/createEvent")
	public String Submit(Model model, @RequestParam String eventName, @RequestParam String eventDesc, 
			@RequestParam String ticketName,  @RequestParam String ticketPrice, @RequestParam String ticketDesc, HttpServletRequest request) {
		
		Principal currentUser = request.getUserPrincipal();
		if (currentUser != null)
			model.addAttribute("loggedUser", currentUser.getName());
		
		try {
			eventUser = userR.findById(currentUser.getName()).get();
		} catch (Exception e) {
			return "redirect:/dbError";
		}
		
		if(eventName != null && eventDesc != null && ticketName != null && ticketDesc != null && ticketPrice != null && eventUser != null) {
			
			
			Event event = new Event(eventUser, eventName, eventDesc);
			
			float price = Float.parseFloat(ticketPrice);
			Ticket ticket = new Ticket(price, ticketName, ticketDesc, event);
			try {
				eventR.save(event);
				ticketR.save(ticket);
			} catch (Exception e) {
				return "redirect:/createEvent";
			}
			
			event.addTicket(ticket);
			try {
				eventR.save(event);
			} catch (Exception e) {
				return "redirect:/createEvent";
			}
			
			return "redirect:/";
		} else {
			
			return "redirect:/createEvent";
			
		}
		
	}
	
}