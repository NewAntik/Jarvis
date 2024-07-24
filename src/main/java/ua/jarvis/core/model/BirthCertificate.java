package ua.jarvis.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "birth_certificates")
public class BirthCertificate extends DocumentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@Size(max = 2)
	@Column(length = 2, name = "day")
	private String day;

	@Size(max = 2)
	@Column(length = 2, name = "month")
	private String month;

	@Size(max = 4)
	@Column(length = 4, name = "year")
	private String year;

	@Size(max = 8)
	@Column(length = 8, name = "number")
	private String number;

	@ManyToOne
	@JoinColumn(name = "address_id")
	private Address birthAddress;

	public BirthCertificate() {
	}

	public String getDay() {
		return day;
	}

	public void setDay(final String day) {
		this.day = day;
	}

	public String getMonth() {
		return month;
	}

	private void setMonth(final String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	private void setYear(final String year) {
		this.year = year;
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
			", day=" + day +
			", month=" + month +
			", year=" + year +
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
		return Objects.equals(id, that.id) &&
			Objects.equals(day, that.day) &&
			Objects.equals(month, that.month) &&
			Objects.equals(year, that.year) &&
			Objects.equals(number, that.number) &&
			Objects.equals(birthAddress, that.birthAddress);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, day, month, year, number, birthAddress);
	}
}
