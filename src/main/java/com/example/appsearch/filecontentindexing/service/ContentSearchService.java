package com.example.appsearch.filecontentindexing.service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.example.appsearch.filecontentindexing.model.FileMetadata;
import com.example.appsearch.filecontentindexing.model.SearchRequest;

@Service
public class ContentSearchService {
	
	
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
		ResponseEntity<String> postForEntity = restTemplate.postForEntity(appsearchDocumentsUrl, new HttpEntity<>(metadata,appsearchHeaders),String.class);
		return postForEntity.toString();
	}
	
	public String indexContentFile(MultipartFile file) throws IllegalStateException, IOException, TikaException {
		Tika tika=new Tika();	
		String filecontent = tika.parseToString(file.getInputStream());
		String fileType = tika.detect(file.getInputStream());
		file.transferTo(new File(uploadDir+file.getOriginalFilename()));
		
		FileMetadata metadata=new FileMetadata();
		metadata.setFilename(file.getOriginalFilename());
		metadata.setFilesize(String.valueOf(file.getSize()));
		metadata.setId(UUID.randomUUID().toString());
		metadata.setUploaddirectory(uploadDir+file.getOriginalFilename());		
		metadata.setFilecontent(filecontent);
		metadata.setFiletype(fileType);
		createFile(metadata);
		return "file-Uploaded";
	}
	
	public String searchFile(SearchRequest request) {
		ResponseEntity<String> postForEntity = restTemplate.postForEntity(appsearchSearchUrl, new HttpEntity<>(request,appsearchHeaders),String.class);
		return postForEntity.toString();
	}
}
