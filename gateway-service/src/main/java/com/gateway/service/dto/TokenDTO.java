package com.gateway.service.dto;

public class TokenDTO {

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public TokenDTO(String token) {
		super();
		this.token = token;
	}

	public TokenDTO() {
		super();
	}
	
}
