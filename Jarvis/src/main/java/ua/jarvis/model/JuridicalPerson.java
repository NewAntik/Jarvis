package ua.jarvis.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
public class JuridicalPerson {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 8)
	@Column(length = 8, name = "erdpo")
	private String erdpo;

	@OneToOne
	@Column(name = "jur_adress")
	private AddressInfo jurAdress;

	@Size(max = 200)
	@Column(length = 200, name = "type_activity")
	private String typeActivity;

	@OneToMany
	@Column(length = 50, name = "email")
	private Email email;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<PhoneInfo> phones = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<CarInfo> car = new HashSet<>();

	public JuridicalPerson() {}


}
