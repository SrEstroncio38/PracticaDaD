package es.urjc.TicTakTicket.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.urjc.TicTakTicket.Entities.Event;
import es.urjc.TicTakTicket.Entities.EventRepository;

@Controller
public class EventsController {
	
	@Autowired
	private EventRepository eventR;

	@RequestMapping(value = {"/events","/events/{num}"})
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

