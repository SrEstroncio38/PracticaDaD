package es.urjc.TicTakTicket.Controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.urjc.TicTakTicket.Entities.Event;
import es.urjc.TicTakTicket.Entities.EventRepository;
import es.urjc.TicTakTicket.Entities.Order;
import es.urjc.TicTakTicket.Entities.OrderRepository;
import es.urjc.TicTakTicket.Entities.Ticket;
import es.urjc.TicTakTicket.Entities.User;
import es.urjc.TicTakTicket.Entities.UserRepository;

@Controller
public class MyEventsController {
	
	User myUser;
	
	@Autowired
	private EventRepository eventR;
	
	@Autowired
	private UserRepository userR;
	
	@Autowired
	private OrderRepository orderR;
	

	@RequestMapping(value = {"/myEvents","/myEvents/{num}"})
	public String Load(Model model, @PathVariable(required = false) String num, HttpServletRequest request) {
		
		Principal currentUser = request.getUserPrincipal();
		if (currentUser != null)
			model.addAttribute("loggedUser", currentUser.getName());
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		model.addAttribute("token", token.getToken());
		
		try {
			myUser = userR.findById(currentUser.getName()).get();
		} catch (Exception e) {
			return "redirect:/dbError";
		}
		
		int numPage = 0;
		int paso = 5;
		boolean prePageFlag = false;
		if (num != null) {
			numPage = Integer.parseInt(num);
			if(numPage <= 0) {
				numPage = 0;
			} else {
				prePageFlag = true;
			}
		}
		Page<Event> events;
		try {
			events = eventR.findByUser(myUser,PageRequest.of(numPage, paso));
		} catch (Exception e) {
			return "redirect:/dbError";
		}
		boolean eventFlag = false;
		if(!events.isEmpty()) {
			eventFlag = true;
		}
		
		model.addAttribute("actualPage", numPage);
		model.addAttribute("prePageFlag",prePageFlag);
		model.addAttribute("prePage", numPage-1);
		model.addAttribute("nextPage", numPage+1);
		model.addAttribute("events", events);
		model.addAttribute("eventFlag", eventFlag);
		model.addAttribute("username", myUser.getUsername());
		model.addAttribute("page_title", "Mis eventos");
		
		return "myEvents_template";
	}
	
	
	@RequestMapping(value = {"/deleteEvent/{id}"})
	public String removeEvent(Model model, @PathVariable(required = false) String id) {
		
		int intId = Integer.parseInt(id);
		Optional<Event> eventToDelete;
		try {
			eventToDelete = eventR.findById(intId);
		} catch (Exception e) {
			return "redirect:/dbError";
		}
		if(eventToDelete.isPresent()) {
			Event event = eventToDelete.get();
			for (Ticket t : event.getTickets()) {
				List<Order> orders;
				try {
					orders = orderR.findByTickets(t);
				} catch (Exception e) {
					return "redirect:/dbError";
				}
				for (Order o : orders) {
					try {
						orderR.delete(o);
					} catch (Exception e) {
						return "redirect:/myEvents";
					}
				}
			}
			
			try {
				eventR.delete(eventToDelete.get());
			} catch (Exception e) {
				return "redirect:/myEvents";
			}
		}
		
		
		return "redirect:/myEvents";
	}
	
}

