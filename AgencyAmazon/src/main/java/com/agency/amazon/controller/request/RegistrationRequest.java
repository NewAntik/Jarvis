package com.agency.amazon.controller.request;

import com.agency.amazon.model.Role;

public class RegistrationRequest {

	private String firstName;

	private String lastName;

	private String login;

	private String password;

	private Role role;

	public RegistrationRequest() {
		this.role = Role.ROLE_STAFF;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(final Role role) {
		this.role = role;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(final String login) {
		this.login = login;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
}
