package es.urjc.TicTakTicket.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.urjc.TicTakTicket.Entities.Event;
import es.urjc.TicTakTicket.Entities.EventRepository;
import es.urjc.TicTakTicket.Entities.Payment;
import es.urjc.TicTakTicket.Entities.PaymentRepository;
import es.urjc.TicTakTicket.Entities.Ticket;
import es.urjc.TicTakTicket.Entities.TicketRepository;
import es.urjc.TicTakTicket.Entities.User;
import es.urjc.TicTakTicket.Entities.UserRepository;

@Controller
public class BuyController {
	
	@Autowired
	private EventRepository eventR;
	
	@Autowired
	private TicketRepository ticketR;
	
	@Autowired
	private PaymentRepository paymentR;
	
	@Autowired
	private UserRepository userR;

	@RequestMapping(value = {"buy/{id}"})
	public String Load(Model model, @PathVariable(required = true) int id) {
		
		Event event = eventR.findById(id).get();
		
		List<Ticket> tickets = ticketR.findByEvent(event);
		
		User currentUser = userR.findById("default").get();
		
		List<Payment> payments = paymentR.findByUser(currentUser);
		
		model.addAttribute("tickets",tickets);
		model.addAttribute("cards", payments);
		model.addAttribute("page_title", "Compra");
		
		return "buy_template";
	}
	
}

