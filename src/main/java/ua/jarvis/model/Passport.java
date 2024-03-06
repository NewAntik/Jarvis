package ua.jarvis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "passports")
public class Passport extends DocumentEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(max = 9)
	@Column(length = 9, name = "passport_number")
	private String passportNumber;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Passport() {}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(final String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Passport{" +
			"id=" + id +
			", passportNumber='" + passportNumber + '\'' +
			", dateIssue=" + issueDate +
			", validUntil=" + validUntil +
			", validity=" + validity +
			", authority='" + authority + '\'' +
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
		final Passport passport = (Passport) o;
		return validity == passport.validity &&
			Objects.equals(id, passport.id) &&
			Objects.equals(passportNumber, passport.passportNumber) &&
			Objects.equals(issueDate, passport.issueDate) &&
			Objects.equals(validUntil, passport.validUntil);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, passportNumber, issueDate, validUntil, validity);
	}
}
