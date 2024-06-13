package ua.jarvis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "birth_certificates")
public class BirthCertificate extends DocumentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Size(max = 10)
	@Column(name = "birthday", length = 20)
	private LocalDateTime birthday;

	@Column(name = "number")
	private String number;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id")
	private Address birthAddress;

	public BirthCertificate() {
	}

	public Address getBirthAddress() {
		return birthAddress;
	}

	public void setBirthAddress(final Address birthAddress) {
		this.birthAddress = birthAddress;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public LocalDateTime getBirthday() {
		return birthday;
	}

	public void setBirthday(final LocalDateTime birthday) {
		this.birthday = birthday;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "BirthCertificate{" +
			"id=" + id +
			", user=" + user +
			", birthday=" + birthday +
			", number='" + number + '\'' +
			", birthAddress=" + birthAddress +
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
		final BirthCertificate that = (BirthCertificate) o;
		return Objects.equals(birthday, that.birthday) && Objects.equals(number, that.number);
	}

	@Override
	public int hashCode() {
		return Objects.hash(birthday, number);
	}
}
