package com.auth.service.dto;

public class AuthUserDTO {

	private String userName;
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AuthUserDTO(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public AuthUserDTO() {
		super();
	}

}
