package es.urjc.TicTakTicket.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardController {

	@RequestMapping("/dashboard")
	public String Load(Model model) {
		
		model.addAttribute("page_title", "Inicio");
		
		return "dashboard_template";
	}
	
}
