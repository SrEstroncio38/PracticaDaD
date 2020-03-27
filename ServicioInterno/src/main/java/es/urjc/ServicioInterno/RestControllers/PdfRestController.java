package es.urjc.ServicioInterno.RestControllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.pdfbox.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

import es.urjc.ServicioInterno.Entities.*;
import es.urjc.ServicioInterno.Entities.OrderRepository;

@RestController
public class PdfRestController {
	
	@Autowired
	private OrderRepository orderR;
	
	@PostMapping("/pdf/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<byte[]> userPrueba() throws DocumentException, IOException {
		
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("iText.pdf"));
		
		document.open();
		//Escribir el PDF
		Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
		Chunk chunk = new Chunk("Hello world", font);
		
		document.add(chunk);
		document.close();
		//Cierras
		byte[] contents = Files.readAllBytes(Paths.get("iText.pdf"));
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
	    // Here you have to set the actual filename of your pdf
	    String filename = "output.pdf";
	    headers.setContentDispositionFormData(filename, filename);
	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	    ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
	    return response;
	}
	
}
