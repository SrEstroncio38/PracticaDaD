package es.urjc.TicTakTicket.Controllers;

import java.util.List;
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
public class ProfileController {
	
	@Autowired
	private PaymentRepository paymentR;
	
	@Autowired
	private UserRepository userR;
	
	User currentUser;
	
	@RequestMapping(value = {"/user","/user/{username}"})
	public String register(Model model, @PathVariable(required = false) String username) {
		
		if(username != null) {
			Optional<User> user = userR.findById(username);
			if (user.isPresent()) {
				currentUser = user.get();
			} 
		} else {
			currentUser = userR.findById("default").get();
		}
		
		List<Payment> payments = paymentR.findByUser(currentUser);
		
		model.addAttribute("cards", payments);
		model.addAttribute("page_title", "Perfil");
		
		return "user_template";
	}
	
	@PostMapping("/deleteCard")
	public String Submit(Model model, @RequestParam String cardNumber) {
		
		paymentR.delete(paymentR.findByCardNumber(cardNumber).get(0));
		
		return "redirect:/user";
	}
}

