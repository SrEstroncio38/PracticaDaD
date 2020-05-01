package es.urjc.TicTakTicket.Controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

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
	public String Load(Model model, @PathVariable(required = true) int id, HttpServletRequest request) {
		
		Event event = eventR.findById(id).get();
		
		List<Ticket> tickets = ticketR.findByEvent(event);
		
		Principal currentUser = request.getUserPrincipal();
		if (currentUser != null)
			model.addAttribute("loggedUser", currentUser.getName());
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		model.addAttribute("token", token.getToken());
		
		User currentUser2 = userR.findById(currentUser.getName()).get();
		
		List<Payment> payments = paymentR.findByUser(currentUser2);
		
		model.addAttribute("tickets",tickets);
		model.addAttribute("cards", payments);
		model.addAttribute("page_title", "Compra");
		
		return "buy_template";
	}
	
	@PostMapping("/pay")
	public String Submit(Model model, @RequestParam(required = false) String selectedCard,
			@RequestParam String person_0, @RequestParam String tickettype_0,
			@RequestParam String person_1, @RequestParam String tickettype_1,
			@RequestParam String person_2, @RequestParam String tickettype_2,
			@RequestParam String person_3, @RequestParam String tickettype_3,
			@RequestParam String person_4, @RequestParam String tickettype_4,
			@RequestParam(required = false) String email,
			HttpServletRequest request
			) {
		
		Principal currentUser = request.getUserPrincipal();
		User currentUser2 = userR.findById(currentUser.getName()).get();
		
		if(selectedCard != "" && selectedCard != null) {
			
			String displayNumber = "";
			for (int i = 0; i < selectedCard.length()-4; i++)
				displayNumber += "*";
			displayNumber += selectedCard.substring(selectedCard.length()-4,selectedCard.length());
			
			List<String> names = new ArrayList<String>();
			List<Ticket> tickets = new ArrayList<Ticket>();
			
			if (!tickettype_0.equals("none") && !person_0.equals("")) {
				names.add(person_0);
				Ticket ticket = ticketR.findById(Integer.parseInt(tickettype_0)).get();
				tickets.add(ticket);
			}
			
			if (!tickettype_1.equals("none") && !person_1.equals("")) {
				names.add(person_1);
				Ticket ticket = ticketR.findById(Integer.parseInt(tickettype_1)).get();
				tickets.add(ticket);
			}
			
			if (!tickettype_2.equals("none") && !person_2.equals("")) {
				names.add(person_2);
				Ticket ticket = ticketR.findById(Integer.parseInt(tickettype_2)).get();
				tickets.add(ticket);
			}
			
			if (!tickettype_3.equals("none") && !person_3.equals("")) {
				names.add(person_3);
				Ticket ticket = ticketR.findById(Integer.parseInt(tickettype_3)).get();
				tickets.add(ticket);
			}
			
			if (!tickettype_4.equals("none") && !person_4.equals("")) {
				names.add(person_4);
				Ticket ticket = ticketR.findById(Integer.parseInt(tickettype_4)).get();
				tickets.add(ticket);
			}
			
			if (!tickets.isEmpty()) {
				Order order = new Order(names, tickets, currentUser2, displayNumber);
				orderR.save(order);
				//TODO enviar email
				if (!email.equals("")) {
					RestTemplate restTemplate = new RestTemplate();
					String url = "http://myhaproxy:8080/email/" + order.getId();
					ResponseEntity<Boolean> response = restTemplate.postForEntity(url, email, Boolean.class);
				}
				
				return "redirect:/events";
			}
		}
		return "redirect:/events";
		
	}

}

