package es.urjc.ServicioInterno.RestControllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfWriter;

import es.urjc.ServicioInterno.Entities.Order;
import es.urjc.ServicioInterno.Entities.OrderRepository;

@RestController
public class EmailRestController {
	
	@Autowired
	public JavaMailSender emailSender;
	
	@Autowired
	private OrderRepository orderR;
	
	@PostMapping("/email/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public boolean emailSend(@RequestBody String email, @PathVariable(required = true) String id) throws DocumentException, IOException, MessagingException {
		
		
		MimeMessage message = emailSender.createMimeMessage();
	      
	    MimeMessageHelper helper = new MimeMessageHelper(message, true);
	     
	    helper.setTo(email);
		    
		helper.setFrom("TicTakTicket");
		helper.setSubject("Recibo de Compra");
		
		int idn = Integer.parseInt(id);
		Optional<Order> order = orderR.findById(idn);
		
		if(!order.isPresent()) return false;
		
		String name = order.get().getUser().getUsername();
		String num = Integer.toString(order.get().getId());
		
		helper.setText("Buenas "+name+",\n\nEste correo es en referencia al pedido número "+num+
		" y se le adjunta la descripción del mismo.\n\nUn cordial saludo,\nTic Tak Ticket.");
		
		//Crear pdf
        Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("Order"+id+".pdf"));
		
		document.open();
		//Escribir el PDF
		Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
		Chunk chunk = new Chunk("Hello world", font);
		
		document.add(chunk);
		document.close();
		
		FileSystemResource file 
	      = new FileSystemResource(new File("Order"+id+".pdf"));
	    helper.addAttachment("Order"+id+".pdf", file);
        
        emailSender.send(message);
		
        //Borras PDF
  		File fileToDelete = new File("Order"+id+".pdf");
  		fileToDelete.delete();
        
		return true;
		
	}
}
