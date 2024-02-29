package com.agency.amazon.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Document
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@Column(name = "first_name")
	@Size(max = 50)
	private String firstName;

	@Column(name = "last_name")
	@Size(max = 50)
	private String lastName;

	@Column
	@Size(max = 100)
	private String password;

	@Column
	@Size(max = 100)
	private String login;

	@Column
	@Size(max = 10)
	private String asin;

	@Column
	@Enumerated(EnumType.STRING)
	private Role role;

	@CreatedDate
	@Column(name = "created_date", nullable = false, updatable = false)
	private Date createdDate;

	public User() {}

	public User
	(
		final String firstName,
		final String lastName,
		final String login,
		final String password,
		final Role role,
		final String asin
	) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.login = login;
		this.password = password;
		this.role = role;
		this.asin = asin;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority(role.toString()));

		return authorities;
	}

	public String getAsin() {
		return asin;
	}

	public void setAsin(final String asin) {
		this.asin = asin;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(final Date createdDate) {
		this.createdDate = createdDate;
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

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "User{" +
			"id='" + id + '\'' +
			", firstName='" + firstName + '\'' +
			", lastName='" + lastName + '\'' +
			", password='" + password + '\'' +
			", login='" + login + '\'' +
			", asin='" + asin + '\'' +
			", role=" + role +
			", createdDate=" + createdDate +
			'}';
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final User user = (User) o;
		return Objects.equals(id, user.id) &&
			Objects.equals(firstName, user.firstName) &&
			Objects.equals(lastName, user.lastName) &&
			Objects.equals(password, user.password) &&
			Objects.equals(login, user.login) &&
			Objects.equals(asin, user.asin) &&
			role == user.role &&
			Objects.equals(createdDate, user.createdDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstName, lastName, password, login, asin, role, createdDate);
	}
}
