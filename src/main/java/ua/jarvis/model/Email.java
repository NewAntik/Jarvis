package ua.jarvis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "emails")
public class Email extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(max = 100)
	@Column(length = 100, name = "email_address")
	private String emailAddress;

	@ManyToOne
	private User user;

	@ManyToOne
	private JuridicalPerson juridicalPerson;

	public Email() {}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(final String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public JuridicalPerson getJuridicalPerson() {
		return juridicalPerson;
	}

	public void setJuridicalPerson(final JuridicalPerson juridicalPerson) {
		this.juridicalPerson = juridicalPerson;
	}

	@Override
	public String toString() {
		return "Email{" +
			"id=" + id +
			", emailAddress='" + emailAddress + '\'' +
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
		final Email email = (Email) o;
		return Objects.equals(id, email.id) && Objects.equals(emailAddress, email.emailAddress);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, emailAddress);
	}
}
