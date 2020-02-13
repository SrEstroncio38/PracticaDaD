package es.urjc.TicTakTicket.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TicketsController {

	@RequestMapping("/tickets")
	public String register(Model model) {
		
		return "tickets_template";
	}
}
