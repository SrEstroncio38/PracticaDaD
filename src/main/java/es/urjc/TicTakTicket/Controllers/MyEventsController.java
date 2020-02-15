package es.urjc.TicTakTicket.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.urjc.TicTakTicket.Entities.Event;
import es.urjc.TicTakTicket.Entities.EventRepository;
import es.urjc.TicTakTicket.Entities.TicketRepository;
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
	private TicketRepository ticketR;
	

	@RequestMapping(value = {"/myEvents","/myEvents/{username}/{num}","/myEvents/{username}"})
	public String Load(Model model, @PathVariable(required = false) String num,
			@PathVariable(required = false) String username) {
		
		if(username != null) {
			Optional<User> user = userR.findById(username);
			if (user.isPresent()) {
				myUser = user.get();
			} 
		} else {
			myUser = userR.findById("default").get();
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
		Page<Event> events = eventR.findByUser(myUser,PageRequest.of(numPage, paso));
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
		//TODO cuando haya login comprobar que el usuario logeado coincide con el del evento
		Optional<Event> eventToDelete = eventR.findById(intId);
		if(eventToDelete.isPresent()) {
			eventR.delete(eventToDelete.get());
		}
		
		
		return "redirect:/myEvents";
	}
	
}

