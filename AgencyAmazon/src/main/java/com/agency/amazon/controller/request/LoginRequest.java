package com.agency.amazon.controller.request;

public class LoginRequest {
	private String login;
	private String password;

	public LoginRequest() {}

	public String getLogin() {
		return login;
	}

	public void setLogin(final String username) {
		this.login = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
}
