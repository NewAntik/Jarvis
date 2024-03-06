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

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "foreign_passports")
public class ForeignPassport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(max = 8)
	@Column(length = 8, name = "passport_number")
	private String passportNumber;

	@NotNull
	@Column(name = "date_issue")
	private LocalDateTime dateIssue;

	@NotNull
	@Column(name = "valid_until")
	private LocalDateTime validUntil;

	@NotNull
	@Column(name = "validity")
	private boolean validity;

	@NotNull
	@Size(max = 200)
	@Column(length = 200, name = "authority")
	private String authority;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public ForeignPassport() {}

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

	public LocalDateTime getDateIssue() {
		return dateIssue;
	}

	public void setDateIssue(final LocalDateTime dateIssue) {
		this.dateIssue = dateIssue;
	}

	public LocalDateTime getValidUntil() {
		return validUntil;
	}

	public void setValidUntil(final LocalDateTime validUntil) {
		this.validUntil = validUntil;
	}

	public boolean isValidity() {
		return validity;
	}

	public void setValidity(final boolean validity) {
		this.validity = validity;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(final String authority) {
		this.authority = authority;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "ForeignPassport{" +
			"id=" + id +
			", passportNumber='" + passportNumber + '\'' +
			", dateIssue=" + dateIssue +
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
		final ForeignPassport that = (ForeignPassport) o;
		return validity == that.validity &&
			Objects.equals(id, that.id) &&
			Objects.equals(passportNumber, that.passportNumber) &&
			Objects.equals(dateIssue, that.dateIssue) &&
			Objects.equals(validUntil, that.validUntil);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, passportNumber, dateIssue, validUntil, validity);
	}
}
