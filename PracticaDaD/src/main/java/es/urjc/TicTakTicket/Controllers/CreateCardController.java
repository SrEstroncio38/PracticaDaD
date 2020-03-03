package es.urjc.TicTakTicket.Controllers;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.urjc.TicTakTicket.Entities.Payment;
import es.urjc.TicTakTicket.Entities.PaymentRepository;
import es.urjc.TicTakTicket.Entities.User;
import es.urjc.TicTakTicket.Entities.UserRepository;

@Controller
public class CreateCardController {
	
	@Autowired
	private PaymentRepository paymentR;
	
	@Autowired
	private UserRepository userR;
	
	User eventUser;
	
	@RequestMapping(value = {"/addCard","/addCard/{username}"})
	public String Load(Model model, @PathVariable(required = false) String username) {
		if (username != null) {
			Optional<User> user = userR.findById(username);
			if (user.isPresent()) {
				eventUser = user.get();
			} 
		} else {
			eventUser = userR.findById("default").get();
		}
		
		model.addAttribute("page_title", "Crear Evento");
		
		return "addCard_template";
	}
	
	@PostMapping("/addCard")
	public String Submit(Model model, @RequestParam String cardNumber, @RequestParam String ownerName, 
			@RequestParam String expireDate,  @RequestParam String cvv) {
		
		if(!cardNumber.equals("") && !ownerName.equals("") && !expireDate.equals("") && !cvv.equals("") && eventUser != null) {
			
			Date d = Date.valueOf(expireDate);
			Short c = Short.valueOf(cvv);
			
			Payment payment = new Payment(eventUser, ownerName, cardNumber, d, c);
			paymentR.save(payment);
			
			return "redirect:/user";
		} else {
			
			return "redirect:/addCard";
			
		}
		
	}
	
	
	
}