package es.urjc.TicTakTicket.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.urjc.TicTakTicket.Entities.Order;
import es.urjc.TicTakTicket.Entities.OrderRepository;
import es.urjc.TicTakTicket.Entities.User;
import es.urjc.TicTakTicket.Entities.UserRepository;


@Controller
public class OrdersController {
	
	@Autowired
	private UserRepository userR;
	
	@Autowired
	private OrderRepository orderR;
	
	User myUser;

	@RequestMapping(value = {"/orders","/orders/{username}/{num}","/orders/{username}"})
	public String register(Model model, @PathVariable(required = false) String username, 
			@PathVariable(required = false) String num) {
		
		if(username != null) {
			Optional<User> user = userR.findById(username);
			if(user.isPresent()) {
				myUser = user.get();
			}
		} else {
			myUser = userR.findById("default").get();
		}
		int numPage = 0;
		int paso = 5;
		boolean prePageFlag = false;
		if (num != null) {
			numPage = Integer.parseInt(num);
			if(numPage <= 0) {
				numPage = 0;
			} else {
				prePageFlag = true;
			}
		}
		Page<Order> orders = orderR.findByUser(myUser,PageRequest.of(numPage, paso));
		boolean orderFlag = false;
		if(!orders.isEmpty()) {
			orderFlag = true;
		}
		
		model.addAttribute("actualPage", numPage);
		model.addAttribute("prePageFlag",prePageFlag);
		model.addAttribute("prePage", numPage-1);
		model.addAttribute("nextPage", numPage+1);
		model.addAttribute("orders", orders);
		model.addAttribute("orderFlag", orderFlag);
		model.addAttribute("name", myUser.getUsername());
		model.addAttribute("page_title", "Orders");
		
		return "orders_template";
	}
}
