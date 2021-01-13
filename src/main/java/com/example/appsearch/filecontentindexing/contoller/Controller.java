package com.example.appsearch.filecontentindexing.contoller;

import java.io.IOException;

import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.appsearch.filecontentindexing.model.FileMetadata;
import com.example.appsearch.filecontentindexing.model.SearchRequest;
import com.example.appsearch.filecontentindexing.service.ContentSearchService;

@RestController
public class Controller {

	@Autowired
	ContentSearchService contentSearchService;
	
	@PostMapping(value="/create")
	public @ResponseBody String createFile(@RequestBody FileMetadata metadata) {
		return contentSearchService.createFile(metadata);
	}
	
	@PostMapping(value="/uploadFile")
	public @ResponseBody String uploadFile(@RequestParam("file")  MultipartFile file) throws IllegalStateException, IOException, TikaException {
		return contentSearchService.indexContentFile(file);
	}
	
	@PostMapping(value="/search")
	public @ResponseBody String searchFile(@RequestBody SearchRequest request) {
		return contentSearchService.searchFile(request);
	}
}
