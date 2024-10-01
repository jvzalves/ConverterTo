package com.jvzalves.converterto.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

@Service
public class PdfToWordService {
	
	 public void convertPdfToWord(InputStream pdfInputStream, OutputStream wordOutputStream) throws IOException {
	        try (PDDocument pdfDocument = PDDocument.load(pdfInputStream);
	             XWPFDocument wordDocument = new XWPFDocument()) {

	            PDFTextStripper pdfStripper = new PDFTextStripper();
	            String pdfText = pdfStripper.getText(pdfDocument);

	            wordDocument.createParagraph().createRun().setText(pdfText);

	            wordDocument.write(wordOutputStream);
	        }
	    }
	}


