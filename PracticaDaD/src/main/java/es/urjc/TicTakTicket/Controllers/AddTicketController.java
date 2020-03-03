package es.urjc.TicTakTicket.Controllers;

import java.util.Optional;

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


@Controller
public class AddTicketController {
	
	@Autowired
	private EventRepository eventR;
	
	@Autowired
	private TicketRepository ticketR;
	
	@RequestMapping(value = {"/addTicket/{id}"})
	public String Load(Model model, @PathVariable(required = false) String id) {
		
		int eventId = Integer.parseInt(id);
		//TODO cuando haya login comprobar que el usuario logeado coincide con el del evento
		model.addAttribute("eventId", eventId);
		model.addAttribute("page_title", "Añadir Ticket");
		return "addTicket_template";
		
	}
	
	@PostMapping(value = {"/addTicket/{id}"})
	public String addTicket(Model model, @PathVariable(required = false) String id,
			@RequestParam String ticketName,  @RequestParam String ticketPrice, @RequestParam String ticketDesc) {
		
		int eventId = Integer.parseInt(id);
		//TODO cuando haya login comprobar que el usuario logeado coincide con el del evento
		Optional<Event> eventToAdd = eventR.findById(eventId);
		if(eventToAdd.isPresent()) {
			Event eA = eventToAdd.get();
			if (ticketName != null && ticketPrice != null && ticketDesc != null) {
				float price = Float.parseFloat(ticketPrice);
				Ticket ticket = new Ticket(price, ticketName, ticketDesc, eA);
				eA.addTicket(ticket);
				ticketR.save(ticket);
				eventR.save(eA);
			} else {
				return "redirect:/myEvents";
			}
		} else {
			return "redirect:/myEvents";
		}
		
		return "redirect:/myEvents";
		
	}
}
