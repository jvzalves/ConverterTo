package com.jvzalves.converterto.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

@Service
public class WordToPdfService {
	
	  public void convertWordToPdf(InputStream wordInputStream, OutputStream pdfOutputStream) throws IOException {
	        try (XWPFDocument document = new XWPFDocument(wordInputStream);
	             PDDocument pdfDocument = new PDDocument()) {

	            PDPage page = new PDPage();
	            pdfDocument.addPage(page);

	            PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page);
	            contentStream.setFont(PDType1Font.HELVETICA, 12);
	            contentStream.beginText();
	            contentStream.setLeading(14.5f);
	            contentStream.newLineAtOffset(50, 750);

	            document.getParagraphs().forEach(paragraph -> {
	                try {
	                    contentStream.showText(paragraph.getText());
	                    contentStream.newLine();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            });

	            contentStream.endText();
	            contentStream.close();
	            pdfDocument.save(pdfOutputStream);
	       }
	  }
}
