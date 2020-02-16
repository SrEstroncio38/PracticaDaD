package es.urjc.TicTakTicket.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.urjc.TicTakTicket.Entities.Event;
import es.urjc.TicTakTicket.Entities.EventRepository;
import es.urjc.TicTakTicket.Entities.Order;
import es.urjc.TicTakTicket.Entities.OrderRepository;
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
	private OrderRepository orderR;
	
	@Autowired
	private PaymentRepository paymentR;
	
	@Autowired
	private UserRepository userR;

	@RequestMapping(value = {"/buy/{id}"})
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
	
	@PostMapping("/pay")
	public String Submit(Model model, @RequestParam(required = false) String selectedCard,
			@RequestParam String person_0, @RequestParam String price_0, @RequestParam String tickettype_0,
			@RequestParam String person_1, @RequestParam String price_1, @RequestParam String tickettype_1,
			@RequestParam String person_2, @RequestParam String price_2, @RequestParam String tickettype_2,
			@RequestParam String person_3, @RequestParam String price_3, @RequestParam String tickettype_3,
			@RequestParam String person_4, @RequestParam String price_4, @RequestParam String tickettype_4
			) {
		
		System.out.println("conseguido");
		
		User currentUser = userR.findById("default").get();
		
		if(selectedCard != "" && selectedCard != null) {
			
			String displayNumber = "";
			for (int i = 0; i < selectedCard.length()-4; i++)
				displayNumber += "*";
			displayNumber += selectedCard.substring(selectedCard.length()-4,selectedCard.length());
			
			List<String> names = new ArrayList<String>();
			List<Ticket> tickets = new ArrayList<Ticket>();
			
			if (person_0 != "") {
				names.add(person_0);
				Ticket ticket = ticketR.findById(Integer.parseInt(tickettype_0)).get();
				tickets.add(ticket);
			}
			
			if (person_1 != "") {
				names.add(person_1);
				Ticket ticket = ticketR.findById(Integer.parseInt(tickettype_1)).get();
				tickets.add(ticket);
			}
			
			if (person_2 != "") {
				names.add(person_2);
				Ticket ticket = ticketR.findById(Integer.parseInt(tickettype_2)).get();
				tickets.add(ticket);
			}
			
			if (person_3 != "") {
				names.add(person_3);
				Ticket ticket = ticketR.findById(Integer.parseInt(tickettype_3)).get();
				tickets.add(ticket);
			}
			
			if (person_4 != "") {
				names.add(person_4);
				Ticket ticket = ticketR.findById(Integer.parseInt(tickettype_4)).get();
				tickets.add(ticket);
			}
			
			if (!tickets.isEmpty()) {
				Order order = new Order(names, tickets, currentUser, displayNumber);
				orderR.save(order);
				return "redirect:/events";
			}
			return "redirect:/user";
			
		} else {
			
			return "redirect:/user";
			
		}
		
	}

}

