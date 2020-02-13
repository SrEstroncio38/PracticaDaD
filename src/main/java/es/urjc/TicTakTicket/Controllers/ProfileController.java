package es.urjc.TicTakTicket.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProfileController {

	@RequestMapping("/user")
	public String register(Model model) {
		
		model.addAttribute("page_title", "Perfil");
		
		return "user_template";
	}
}

