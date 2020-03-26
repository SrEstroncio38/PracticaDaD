package es.urjc.TicTakTicket.Controllers;

import java.security.Principal;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {
	
	@RequestMapping("/error")
	public String handleError(Model model, HttpServletRequest request) {
		
		Principal currentUser = request.getUserPrincipal();
		if (currentUser != null)
			model.addAttribute("loggedUser", currentUser.getName());
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		model.addAttribute("token", token.getToken());
		
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	     
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	     
	        if(statusCode == HttpStatus.NOT_FOUND.value()) {
	        	model.addAttribute("errorNum", "404");
	        	model.addAttribute("errorName", "Not Found");
	        	model.addAttribute("errorMsg", "Lo sentimos, no hemos podido encontrar la página específicada");
	        	model.addAttribute("page_title", "Error 404");
	            return "error_template";
	        }
	        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	        	model.addAttribute("errorNum", "500");
	        	model.addAttribute("errorName", "Internal Server Error");
	        	model.addAttribute("errorMsg", "Parece ser que ha ocurrido un error insesperado, por favor, inténtelo de nuevo más tarde");
	        	model.addAttribute("page_title", "Error 500");
	            return "error_template";
	        }
	    }
	    return "error_template";
	}

	@Override
	public String getErrorPath() {
		
		return "/error";
	}
}
