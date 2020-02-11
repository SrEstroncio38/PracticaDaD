package es.urjc.TicTakTicket;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {

	@RequestMapping("/demo")
	public String Demo(Model model) {
		
		return "demo_template";
	}
	
}
