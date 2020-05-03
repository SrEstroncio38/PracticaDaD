package es.urjc.TicTakTicket.Controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	@RequestMapping(value = {"/user"})
	public String register(Model model, HttpServletRequest request) {
		
		Principal currentUser = request.getUserPrincipal();
		if (currentUser != null)
			model.addAttribute("loggedUser", currentUser.getName());
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		model.addAttribute("token", token.getToken());
		
		User currentUser2;
		try {
			currentUser2 = userR.findById(currentUser.getName()).get();
		} catch (Exception e) {
			return "redirect:/dbError";
		}
		
		List<Payment> payments;
		try {
			payments = paymentR.findByUser(currentUser2);
		} catch (Exception e) {
			return "redirect:/dbError";
		}
		
		model.addAttribute("cards", payments);
		model.addAttribute("page_title", "Perfil");
		
		return "user_template";
	}
	
	@PostMapping("/deleteCard")
	public String Submit(Model model, @RequestParam String cardNumber) {
		
		try {
			paymentR.delete(paymentR.findByCardNumber(cardNumber).get(0));
		} catch (Exception e) {
			return "redirect:/user";
		}
		
		return "redirect:/user";
	}
}

