package es.urjc.ServicioInterno.RestControllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import org.apache.pdfbox.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

import es.urjc.ServicioInterno.Entities.*;
import es.urjc.ServicioInterno.Entities.OrderRepository;

@RestController
public class PdfRestController {
	
	@Autowired
	private OrderRepository orderR;
	
	@PostMapping("/pdf/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<byte[]> userPrueba(@PathVariable(required = true) String id) throws DocumentException, IOException {
		
		Optional<Order> order = orderR.findById(Integer.parseInt(id));
		//Devuelve un PDF de error
		if(!order.isPresent()) {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream("Order"+id+"Error.pdf"));
			
			document.open();
			//Escribir el PDF
			Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.BLACK);
			Chunk chunk = new Chunk("La compra con id: "+id+" ha sido eliminada o no se ha podido recuperar", font);
			
			document.add(chunk);
			document.close();
			//Cierras
			byte[] contents = Files.readAllBytes(Paths.get("Order"+id+"Error.pdf"));
			//Borras PDF
			File fileToDelete = new File("Order"+id+"Error.pdf");
			fileToDelete.delete();
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
		   
		    String filename = "Order"+id+"Error.pdf";
		    headers.setContentDispositionFormData(filename, filename);
		    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		    ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
		    return response;
		} else {
			
			Order nOrder = order.get();
			String name = nOrder.getUser().getUsername();
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
			//Cierras
			byte[] contents = Files.readAllBytes(Paths.get("Order"+id+".pdf"));
			//Borras PDF
			File fileToDelete = new File("Order"+id+".pdf");
			fileToDelete.delete();
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
		   
		    String filename = "Order"+id+".pdf";
		    headers.setContentDispositionFormData(filename, filename);
		    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		    ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
		    return response;
			
		}
		
		
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
