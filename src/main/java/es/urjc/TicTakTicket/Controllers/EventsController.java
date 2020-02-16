package es.urjc.TicTakTicket.Controllers;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.urjc.TicTakTicket.Entities.Event;
import es.urjc.TicTakTicket.Entities.EventRepository;
import es.urjc.TicTakTicket.Entities.User;
import es.urjc.TicTakTicket.Entities.UserRepository;

@Controller
public class EventsController {
	
	@Autowired
	private EventRepository eventR;
	

	@Autowired
	UserRepository userR;

	
	@PostConstruct
	public void init() {
		//Creación usuarios
		User a = new User("default", "1234");
		userR.save(a);
		User d = new User("David", "1234");
		userR.save(d);
		User c = new User("Carlos", "1234");
		userR.save(c);
		User z = new User("Zoe", "1234");
		userR.save(z);
		User n = new User("Natalia", "1234");
		userR.save(n);
	
	}

	@RequestMapping(value = {"/","/events","/events/{num}"})
	public String Load(Model model, @PathVariable(required = false) String num) {
		
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
		Page<Event> events = eventR.findAll(PageRequest.of(numPage, paso));

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
		model.addAttribute("page_title", "Eventos");
		
		return "events_template";
	}
	
}

