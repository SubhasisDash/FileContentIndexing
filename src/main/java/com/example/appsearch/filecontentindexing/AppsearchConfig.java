package com.example.appsearch.filecontentindexing;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class AppsearchConfig {
	
	@Value("${appsearch.urlscheme}")
	String urlscheme;
	
	@Value("${appsearch.domainurl}")
	String domainurl;
	
	@Value("${appsearch.port}")
	String port;
	
	@Value("${appsearch.engine}")
	String engine;
	
	@Value("${appsearch.accesskey}")
	String accesskey;
	
	public static final String APPSEARCH_INDEX="/api/as/v1/engines/{engine}/documents";

	public static final String APPSEARCH_SEARCH="/api/as/v1/engines/{engine}/search";
	
	@Bean(name="appsearchDocumentsUrl")
	public URI createurl() {
		Map<String, String> uriDynamicTagsMap=new HashMap<String, String>();
		uriDynamicTagsMap.put("engine", engine);
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
		.scheme(urlscheme)
		.host(domainurl)
		.port(port)
		.path(APPSEARCH_INDEX)
		.buildAndExpand(uriDynamicTagsMap);
		return uriComponents.toUri();
	}
	
	@Bean(name="appsearchSearchUrl")
	public URI createSearchurl() {
		Map<String, String> uriDynamicTagsMap=new HashMap<String, String>();
		uriDynamicTagsMap.put("engine", engine);
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
		.scheme(urlscheme)
		.host(domainurl)
		.port(port)
		.path(APPSEARCH_SEARCH)
		.buildAndExpand(uriDynamicTagsMap);
		return uriComponents.toUri();
	}
	
	@Bean(name="appsearchHeaders")
	public HttpHeaders getHeaders() {
		HttpHeaders headers=new HttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION, new StringBuilder().append("Bearer ").append(accesskey).toString());
		headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}
	
	@Bean
	RestTemplate getResttemplate() {
		RestTemplate restemplate=new RestTemplate();
		return restemplate;
	}
}
