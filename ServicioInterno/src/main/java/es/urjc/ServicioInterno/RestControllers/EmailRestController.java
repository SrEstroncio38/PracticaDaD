package es.urjc.ServicioInterno.RestControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailRestController {
	
	@Autowired
	public JavaMailSender emailSender;
	
	@PostMapping("/email/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public boolean emailSend() {
		
		
		SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo("d.fontela.1998@gmail.com"); 
        message.setSubject("Este correo es de prueba"); 
        message.setText("Este correo es aun mas de prueba");
        emailSender.send(message);
		
		return true;
		
	}
}
