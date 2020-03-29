package es.urjc.ServicioInterno.RestControllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

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
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import es.urjc.ServicioInterno.Entities.Order;
import es.urjc.ServicioInterno.Entities.OrderRepository;
import es.urjc.ServicioInterno.Entities.Ticket;

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
		
		helper.setFrom("TicTakTicket","Tic Tak Ticket");
		helper.setSubject("Recibo de Compra");
		
		int idn = Integer.parseInt(id);
		Optional<Order> order = orderR.findById(idn);
		
		if(!order.isPresent()) return false;
		
		String name = order.get().getUser().getUsername();
		String num = Integer.toString(order.get().getId());
		
		helper.setText("Buenas "+name+",\n\nEste correo es en referencia al pedido número "+num+
		" y se le adjunta la descripción del mismo.\n\nUn cordial saludo,\nTic Tak Ticket.");
		
		//Crear pdf
		Order nOrder = order.get();
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("Order"+id+".pdf"));
		
		document.open();
		//Escribir el PDF
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
		Paragraph chunk = new Paragraph("Datos de la compra "+id+" de "+name, font);
		addEmptyLine(chunk, 1);
		PdfPTable table = new PdfPTable(3);
        
		addTableHeader(table);
		ArrayList<String> names = (ArrayList<String>) nOrder.getNames();
		int j = 0;
		for (Ticket t : nOrder.getTickets()) {
			if (names.size() == j) {
				break;
			} else {
				table.addCell(names.get(j));
				table.addCell(t.getName());
				table.addCell(Float.toString(t.getPrice())+" €");
				j++;
			}
		}
		
		document.add(chunk);
		document.add(table);
		
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
	
	public void addTableHeader(PdfPTable table) {
		Stream.of("Nombre", "Ticket", "Precio")
	      .forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        header.setBorderWidth(2);
	        header.setPhrase(new Phrase(columnTitle));
	        table.addCell(header);
	    });
	}
	
	private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
