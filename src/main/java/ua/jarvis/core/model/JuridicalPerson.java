package ua.jarvis.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "juridical_persons")
public class JuridicalPerson extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 8)
	@Column(length = 8, name = "erdpo")
	private String erdpo;

	@Size(max = 200)
	@Column(length = 200, name = "type_activity")
	private String typeActivity;

	@Column(name = "regisrtation_date")
	private LocalDateTime registrationDate;

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<User> users = new HashSet<>();

	@OneToMany(mappedBy = "juridicalPerson", fetch = FetchType.LAZY)
	private Set<Email> emails = new HashSet<>();

	@OneToMany(mappedBy = "juridicalPerson", fetch = FetchType.LAZY)
	private Set<Phone> phones = new HashSet<>();

	@OneToMany(mappedBy = "juridicalPerson", fetch = FetchType.LAZY)
	private Set<Car> car = new HashSet<>();

	@ManyToMany(mappedBy = "juridicalPersons", fetch = FetchType.LAZY)
	private Set<Address> jurAddresses = new HashSet<>();

	public JuridicalPerson() {}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(final LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

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

	public Set<Address> getJurAddresses() {
		return jurAddresses;
	}

	public void setJurAddresses(final Set<Address> jurAddresses) {
		this.jurAddresses = jurAddresses;
	}

	public String getTypeActivity() {
		return typeActivity;
	}

	public void setTypeActivity(final String typeActivity) {
		this.typeActivity = typeActivity;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(final Set<User> users) {
		this.users = users;
	}

	public Set<Email> getEmails() {
		return emails;
	}

	public void setEmails(final Set<Email> emails) {
		this.emails = emails;
	}

	public Set<Phone> getPhones() {
		return phones;
	}

	public void setPhones(final Set<Phone> phones) {
		this.phones = phones;
	}

	public Set<Car> getCar() {
		return car;
	}

	public void setCar(final Set<Car> car) {
		this.car = car;
	}

	@Override
	public String toString() {
		return "JuridicalPerson{" +
			"id=" + id +
			", erdpo='" + erdpo + '\'' +
			", typeActivity='" + typeActivity + '\'' +
			", registrationDate=" + registrationDate +
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
		final JuridicalPerson that = (JuridicalPerson) o;
		return Objects.equals(erdpo, that.erdpo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(erdpo);
	}
}
