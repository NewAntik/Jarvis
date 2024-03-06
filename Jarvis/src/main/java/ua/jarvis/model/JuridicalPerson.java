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
import jakarta.validation.constraints.Size;

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

	@OneToOne
	private Address jurAddress;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Email> emails = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Phone> phones = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Car> car = new HashSet<>();

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
			Objects.equals(typeActivity, that.typeActivity);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, erdpo, typeActivity);
	}
}
