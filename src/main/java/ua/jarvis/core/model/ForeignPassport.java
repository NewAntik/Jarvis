package ua.jarvis.core.model;

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
@Table(name = "foreign_passports")
public class ForeignPassport extends DocumentEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(max = 8)
	@Column(length = 8, name = "passport_number")
	private String passportNumber;

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
			", user=" + user +
			", issueDate=" + issueDate +
			", validUntil=" + validUntil +
			", isValid=" + isValid +
			", authority='" + authority + '\'' +
			", isUnlimited=" + isUnlimited +
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
		return Objects.equals(passportNumber, that.passportNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(passportNumber);
	}
}
