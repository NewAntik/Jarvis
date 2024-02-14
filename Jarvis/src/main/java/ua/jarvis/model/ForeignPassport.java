package ua.jarvis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;

import java.util.Objects;
import java.util.UUID;

@Entity
public class ForeignPassport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 10)
	@Column(length = 10, name = "sex")
	private String sex;

	@Size(max = 9)
	@Column(length = 9, name = "passport_number")
	private String passportNumber;

	@Size(max = 50)
	@Column(length = 50, name = "name")
	private String name;

	@Size(max = 50)
	@Column(length = 50, name = "sur_name")
	private String surName;

	@ManyToOne
	private User user;

	public ForeignPassport() {}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(final String sex) {
		this.sex = sex;
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

	private String getName() {
		return name;
	}

	private void setName(final String name) {
		this.name = name;
	}

	private String getSurName() {
		return surName;
	}

	private void setSurName(final String surName) {
		this.surName = surName;
	}

	@Override
	public String toString() {
		return "ForeignPassport{" +
			"id=" + id +
			", sex='" + sex + '\'' +
			", passportNumber='" + passportNumber + '\'' +
			", name='" + name + '\'' +
			", surName='" + surName + '\'' +
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
		return Objects.equals(id, that.id) &&
			Objects.equals(sex, that.sex) &&
			Objects.equals(passportNumber, that.passportNumber) &&
			Objects.equals(name, that.name) &&
			Objects.equals(surName, that.surName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, sex, passportNumber, name, surName);
	}
}
