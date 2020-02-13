package es.urjc.TicTakTicket.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.urjc.TicTakTicket.Entities.Event;
import es.urjc.TicTakTicket.Entities.EventRepository;

@Controller
public class EventsController {
	
	@Autowired
	private EventRepository eventR;

	@RequestMapping("/events")
	public String Load(Model model) {
		
		Page<Event> events = eventR.findAll(PageRequest.of(0, 5));
		
		model.addAttribute("events", events);
		model.addAttribute("page_title", "Eventos");
		
		return "events_template";
	}
	
}

