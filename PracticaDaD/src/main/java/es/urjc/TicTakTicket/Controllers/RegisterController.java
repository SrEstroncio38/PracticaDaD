package es.urjc.TicTakTicket.Controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.urjc.TicTakTicket.Entities.User;
import es.urjc.TicTakTicket.Entities.UserRepository;

@Controller
public class RegisterController {
	
	@Autowired
	private UserRepository userR;

	@RequestMapping("/register")
	public String register(Model model, HttpServletRequest request) {
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		model.addAttribute("token", token.getToken());
		
		return "register_template";
	}
	
	@PostMapping("/register")
	public String Submit(Model model, @RequestParam String username,
			@RequestParam String password1, @RequestParam String password2,
			HttpServletRequest request
			) {
		
		if (!password1.equals(password2)) {
			return "redirect:/register";
		}
		
		if (userR.findById(username).isPresent()) {
			return "redirect:/register";
		}
		
		User u = new User(username, password1);
		userR.save(u);
		
		return "redirect:/login";
		
	}
}
