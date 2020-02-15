package es.urjc.TicTakTicket.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@RequestMapping("/user")
	public String register(Model model) {
		
		User currentUser = userR.findById("default").get();
		
		List<Payment> payments = paymentR.findByUser(currentUser);
		
		model.addAttribute("cards", payments);
		model.addAttribute("page_title", "Perfil");
		
		return "user_template";
	}
}

