package com.ragna.utils.pdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ragna.pojos.animation.Anime;

public class PDFGenerator {

	private PDFGenerator() {}
	
	private static Logger logger = LoggerFactory.getLogger(PDFGenerator.class);
	
	public static ByteArrayInputStream buildPDF(List<Anime> list) {
		
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		try {
			
			PdfWriter.getInstance(document, out);
			document.open();
			
			// Add Text to PDF file ->
        	Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
        	Paragraph para = new Paragraph( "Customized table", font);
        	para.setAlignment(Element.ALIGN_CENTER);
        	document.add(para);
        	document.add(Chunk.NEWLINE);
        	
        	PdfPTable table = new PdfPTable(3);
        	// Add PDF Table Header ->
        	
            Stream.of("ID", "Name", "Gender")
	            .forEach(headerTitle -> {
		              PdfPCell header = new PdfPCell();
		              Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		              header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		              header.setHorizontalAlignment(Element.ALIGN_CENTER);
		              header.setBorderWidth(2);
		              header.setPhrase(new Phrase(headerTitle, headFont));
		              table.addCell(header);
	            });
            
            
            for (Anime anime : list) {
            	PdfPCell idCell = new PdfPCell(new Phrase(String.valueOf(anime.getId())));
            	idCell.setPaddingLeft(4);
            	idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            	idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(idCell);
 
                PdfPCell nameCell = new PdfPCell(new Phrase(anime.getName()));
                nameCell.setPaddingLeft(4);
                nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                nameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(nameCell);
 
                PdfPCell lastNameCell = new PdfPCell(new Phrase(String.valueOf(anime.getGender())));
                lastNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lastNameCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lastNameCell.setPaddingRight(4);
                table.addCell(lastNameCell);
            }
            
            document.add(table);
            
            document.close();
			
		} catch (Exception e) {
			logger.error(e.toString());
		}
		
		return new ByteArrayInputStream(out.toByteArray());
	}
	
}
