package ua.jarvis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "phones")
public class Phone {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 10)
	@Column(length = 10, name = "phone_number")
	private String phoneNumber;

	@Size(max = 20)
	@Column(length = 20, name = "imei")
	private String imei;

	@ManyToOne
	private JuridicalPerson juridicalPerson;

	@ManyToOne
	private User user;

	public Phone() {}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(final String imei) {
		this.imei = imei;
	}

	public JuridicalPerson getJuridicalPerson() {
		return juridicalPerson;
	}

	public void setJuridicalPerson(final JuridicalPerson jurPerson) {
		this.juridicalPerson = jurPerson;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Phone{" +
			"id=" + id +
			", phoneNumber='" + phoneNumber + '\'' +
			", imei='" + imei + '\'' +
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
		final Phone phone = (Phone) o;
		return Objects.equals(id, phone.id) &&
			Objects.equals(phoneNumber, phone.phoneNumber) &&
			Objects.equals(imei, phone.imei);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, phoneNumber, imei);
	}
}
