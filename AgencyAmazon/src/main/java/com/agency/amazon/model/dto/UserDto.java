package com.agency.amazon.model.dto;

import com.agency.amazon.model.Role;

import java.util.Date;

public class UserDto {

	private String firstName;

	private String lastName;

	private Role role;

	private Date createdDate;

	private String asin;

	public UserDto() {}

	public UserDto
	(
		final String firstName,
		final String lastName,
		final Role role,
		final Date createdDate,
		final String asin
	) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.createdDate = createdDate;
		this.asin = asin;
	}

	public String getAsin() {
		return asin;
	}

	public void setAsin(final String asin) {
		this.asin = asin;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(final Role role) {
		this.role = role;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(final Date createdDate) {
		this.createdDate = createdDate;
	}
}
