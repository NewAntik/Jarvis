package ua.jarvis.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "juridical_persons")
public class JuridicalPerson {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 8)
	@Column(length = 8, name = "erdpo")
	private String erdpo;

	@Size(max = 200)
	@Column(length = 200, name = "type_activity")
	private String typeActivity;

	@OneToOne(mappedBy = "juridicalPerson")
	private Address jurAddress;

	@NotNull
	@Column(name = "regisrtation_date")
	private LocalDateTime registrationDate;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "juridicalPerson", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Email> emails = new HashSet<>();

	@OneToMany(mappedBy = "juridicalPerson", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Phone> phones = new HashSet<>();

	@OneToMany(mappedBy = "juridicalPerson", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Car> car = new HashSet<>();

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

	public Address getJurAddress() {
		return jurAddress;
	}

	public void setJurAddress(final Address jurAdress) {
		this.jurAddress = jurAdress;
	}

	public String getTypeActivity() {
		return typeActivity;
	}

	public void setTypeActivity(final String typeActivity) {
		this.typeActivity = typeActivity;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
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
			", jurAddress=" + jurAddress +
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
		return Objects.equals(id, that.id) &&
			Objects.equals(erdpo, that.erdpo) &&
			Objects.equals(typeActivity, that.typeActivity) &&
			Objects.equals(jurAddress, that.jurAddress) &&
			Objects.equals(registrationDate, that.registrationDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, erdpo, typeActivity, jurAddress, registrationDate);
	}
}
