package com.example.appsearch.filecontentindexing.model;

public class FileMetadata {

	private String id;
	private String filename;
	private String filesize;
	private String filetype;
	private String uploaddirectory;
	private String filecontent;

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public String getUploaddirectory() {
		return uploaddirectory;
	}	

	public void setUploaddirectory(String uploaddirectory) {
		this.uploaddirectory = uploaddirectory;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilesize() {
		return filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	public String getFilecontent() {
		return filecontent;
	}

	public void setFilecontent(String filecontent) {
		this.filecontent = filecontent;
	}

}
