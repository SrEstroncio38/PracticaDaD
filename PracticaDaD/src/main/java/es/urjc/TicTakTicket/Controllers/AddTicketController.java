package es.urjc.TicTakTicket.Controllers;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
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


@Controller
public class AddTicketController {
	
	@Autowired
	private EventRepository eventR;
	
	@Autowired
	private TicketRepository ticketR;
	
	@RequestMapping(value = {"/addTicket/{id}"})
	public String Load(Model model, @PathVariable(required = false) String id, HttpServletRequest request) {
		
		int eventId = Integer.parseInt(id);
		
		Principal currentUser = request.getUserPrincipal();
		if (currentUser != null)
			model.addAttribute("loggedUser", currentUser.getName());
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		model.addAttribute("token", token.getToken());
		
		if (!checkUser(eventId, currentUser.getName()))
			return "redirect:/myEvents";
		
		model.addAttribute("eventId", eventId);
		model.addAttribute("page_title", "Añadir Ticket");
		return "addTicket_template";
		
	}
	
	@PostMapping(value = {"/addTicket/{id}"})
	public String addTicket(Model model, @PathVariable(required = false) String id,
			@RequestParam String ticketName,  @RequestParam String ticketPrice, @RequestParam String ticketDesc,
			HttpServletRequest request) {
		
		int eventId = Integer.parseInt(id);

		Principal currentUser = request.getUserPrincipal();
		if (!checkUser(eventId,currentUser.getName()))
			return "redirect:/myEvents";
		
		Optional<Event> eventToAdd;
		try {
			eventToAdd = eventR.findById(eventId);
		} catch (Exception e) {
			return "redirect:/dbError";
		}
		if(eventToAdd.isPresent()) {
			Event eA = eventToAdd.get();
			if (ticketName != null && ticketPrice != null && ticketDesc != null) {
				float price = Float.parseFloat(ticketPrice);
				Ticket ticket = new Ticket(price, ticketName, ticketDesc, eA);
				eA.addTicket(ticket);
				try {
					ticketR.save(ticket);
					eventR.save(eA);
				} catch (Exception e) {
					return "redirect:/myEvents";
				}
			} else {
				return "redirect:/myEvents";
			}
		} else {
			return "redirect:/myEvents";
		}
		
		return "redirect:/myEvents";
		
	}
	
	public boolean checkUser (int id, String username) {
		return eventR.findById(id).get().getUser().getUsername().equals(username);
	}
}
