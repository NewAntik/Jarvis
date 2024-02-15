package ua.jarvis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
public class Passport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 9)
	@Column(length = 9, name = "passport_number")
	private String passportNumber;

	@ManyToOne
	private JuridicalPerson jurPerson;

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

	public JuridicalPerson getJurPerson() {
		return jurPerson;
	}

	public void setJurPerson(final JuridicalPerson jurPerson) {
		this.jurPerson = jurPerson;
	}

	@Override
	public String toString() {
		return "Passport{" +
			"id=" + id +
			", passportNumber='" + passportNumber + '\'' +
			", jurPerson=" + jurPerson +
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
		return Objects.equals(id, passport.id) &&
			Objects.equals(passportNumber, passport.passportNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, passportNumber);
	}


}
