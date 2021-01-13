package com.example.appsearch.filecontentindexing.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.util.UUID;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import com.example.appsearch.filecontentindexing.model.FileMetadata;
import com.example.appsearch.filecontentindexing.model.SearchRequest;

@Service
public class ContentSearchService {

	private static Logger logger = LoggerFactory.getLogger(ContentSearchService.class);
	@Autowired
	HttpHeaders appsearchHeaders;

	@Autowired
	URI appsearchSearchUrl;

	@Autowired
	URI appsearchDocumentsUrl;

	@Autowired
	RestTemplate restTemplate;

	@Value("${file.upload-dir}")
	String uploadDir;

	public String createFile(FileMetadata metadata) {
		logger.info("Indexing file to appsearch");
		ResponseEntity<String> postForEntity = restTemplate.postForEntity(appsearchDocumentsUrl,
				new HttpEntity<>(metadata, appsearchHeaders), String.class);
		return postForEntity.toString();
	}

	public String indexContentFile(MultipartFile file)
			throws IllegalStateException, IOException, TikaException, SAXException {

		Tika tika = new Tika();
		InputStream filestream = file.getInputStream();
		String fileType = tika.detect(filestream);
		logger.info("File Type::::::::::: {}", fileType);

		logger.info("File Content Parsed");
		Parser parser = new AutoDetectParser();
		ContentHandler handler = new BodyContentHandler(-1);
		Metadata metadata = new Metadata();
		ParseContext context = new ParseContext();
		parser.parse(file.getInputStream(), handler, metadata, context);
		String fileContent = handler.toString();
		
		//upload file code
		//file.transferTo(new File(uploadDir + file.getOriginalFilename()));
		
		FileMetadata fileMetadata = new FileMetadata();
		fileMetadata.setFilename(file.getOriginalFilename());
		fileMetadata.setFilesize(String.valueOf(file.getSize()));
		fileMetadata.setId(UUID.randomUUID().toString());
		fileMetadata.setUploaddirectory(uploadDir + file.getOriginalFilename());
		fileMetadata.setFilecontent(fileContent);
		fileMetadata.setFiletype(fileType);
		createFile(fileMetadata);
		
		return "File Uploaded";
	}


	private String parseText(InputStream file) throws IOException, TikaException {
		Tika tika = new Tika();
		return tika.parseToString(file);
	}

	private String parseOfficeFile(InputStream file) throws IOException, SAXException, TikaException {
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		ParseContext pcontext = new ParseContext();
		OOXMLParser msofficeparser = new OOXMLParser();
		msofficeparser.parse(file, handler, metadata, pcontext);
		return handler.toString();
	}

	private String parsePdfFile(InputStream file) throws IOException, SAXException, TikaException {
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		ParseContext pcontext = new ParseContext();
		PDFParser pdfparser = new PDFParser();
		pdfparser.parse(file, handler, metadata, pcontext);
		return handler.toString();
	}

	public String searchFile(SearchRequest request) {
		logger.info("Searching file from appsearch");
		ResponseEntity<String> postForEntity = restTemplate.postForEntity(appsearchSearchUrl,
				new HttpEntity<>(request, appsearchHeaders), String.class);
		return postForEntity.toString();
	}
}
