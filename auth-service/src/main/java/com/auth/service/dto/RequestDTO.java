package com.auth.service.dto;

public class RequestDTO {

	private String uri;
	private String method; // Si es POST , GET o lo que sea

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public RequestDTO(String uri, String method) {
		super();
		this.uri = uri;
		this.method = method;
	}

	public RequestDTO() {

	}

}
