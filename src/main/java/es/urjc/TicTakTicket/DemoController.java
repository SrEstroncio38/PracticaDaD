package es.urjc.TicTakTicket;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.urjc.TicTakTicket.Entities.User;
import es.urjc.TicTakTicket.Entities.UserRepository;

@Controller
public class DemoController {
	
	@Autowired
	private UserRepository repository;
	
	@PostConstruct
	public void init() {
		repository.save(new User("Paco","1234"));
		
	}

	@RequestMapping("/demo")
	public String Demo(Model model) {
		
		model.addAttribute("anuncios", repository.findAll());
		
		return "demo_template";
	}
	
}
