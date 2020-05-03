package es.urjc.TicTakTicket.Controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.web.csrf.CsrfToken;
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

	@RequestMapping(value = {"/orders","/orders/{num}"})
	public String register(Model model, @PathVariable(required = false) String num, HttpServletRequest request) {
		
		Principal currentUser = request.getUserPrincipal();
		if (currentUser != null)
			model.addAttribute("loggedUser", currentUser.getName());
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		model.addAttribute("token", token.getToken());
		
		try {
			myUser = userR.findById(currentUser.getName()).get();
		} catch (Exception e) {
			return "redirect:/dbError";
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
		Page<Order> orders;
		try {
			orders = orderR.findByUser(myUser,PageRequest.of(numPage, paso));
		} catch (Exception e) {
			return "redirect:/dbError";
		}
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
