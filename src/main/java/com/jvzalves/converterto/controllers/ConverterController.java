package com.jvzalves.converterto.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jvzalves.converterto.services.PdfToWordService;
import com.jvzalves.converterto.services.WordToPdfService;

@RestController
@RequestMapping("/api/converter")
public class ConverterController {

	@Autowired
	private WordToPdfService wordToPdfService;

	@Autowired
	private PdfToWordService pdfToWordService;

	@PostMapping(value = "/word-to-pdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<byte[]> convertWordToPdf(@RequestParam("file") MultipartFile file) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		wordToPdfService.convertWordToPdf(file.getInputStream(), outputStream);
		byte[] pdfBytes = outputStream.toByteArray();

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=document.pdf")
				.contentType(MediaType.APPLICATION_PDF).body(pdfBytes);

	}

	@PostMapping(value = "/pdf-to-word", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<byte[]> convertPdfToWord(@RequestParam("file") MultipartFile file) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		pdfToWordService.convertPdfToWord(file.getInputStream(), outputStream);
		byte[] wordBytes = outputStream.toByteArray();

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=document.docx")
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(wordBytes);
	}
}
