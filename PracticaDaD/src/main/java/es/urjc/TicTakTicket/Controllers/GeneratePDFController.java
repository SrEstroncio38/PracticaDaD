package es.urjc.TicTakTicket.Controllers;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import es.urjc.TicTakTicket.Entities.Order;
import es.urjc.TicTakTicket.Entities.OrderRepository;
import es.urjc.TicTakTicket.Entities.User;
import es.urjc.TicTakTicket.Entities.UserRepository;

@Controller
public class GeneratePDFController {

	@Autowired
	private OrderRepository orderR;
	
	@Autowired
	private UserRepository userR;

	@GetMapping("/generatePDF/{id}")
	public ResponseEntity<byte[]> generatePDF(Model model, @PathVariable(required = true) String id, HttpServletRequest request) {
		
		Principal currentUser = request.getUserPrincipal();
		if (currentUser != null)
			model.addAttribute("loggedUser", currentUser.getName());
		
		Optional<User> currentUser2 = userR.findById(currentUser.getName());
		Optional<Order> currentOrder = orderR.findById(Integer.parseInt(id));
		
		if (currentUser2.isPresent() && currentOrder.isPresent()) {
			User cUser = currentUser2.get();
			Order order = currentOrder.get();
			if (cUser.getUsername() == order.getUser().getUsername()) {
				RestTemplate restTemplate = new RestTemplate();
				String url = "http://myhaproxy:8080/pdf/"+ id;
				ResponseEntity<byte[]> response = restTemplate.postForEntity(url, null, byte[].class);
				return response;
			}
		}
		
		return null;
	}
	
}
