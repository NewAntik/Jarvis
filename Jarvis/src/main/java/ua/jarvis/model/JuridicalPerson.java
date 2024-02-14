package ua.jarvis.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class JuridicalPerson {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 8)
	@Column(length = 8, name = "erdpo")
	private String erdpo;

	@Size(max = 50)
	@Column(length = 50, name = "email")
	private String email;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Passport> passport = new HashSet<>();

	public JuridicalPerson() {}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getErdpo() {
		return erdpo;
	}

	public void setErdpo(final String erdpo) {
		this.erdpo = erdpo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public Set<Passport> getPassport() {
		return passport;
	}

	public void setPassport(final Set<Passport> passport) {
		this.passport = passport;
	}
}
