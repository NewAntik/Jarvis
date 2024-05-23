package ua.jarvis.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "addresses")
public class Address extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(max = 50)
	@Column(length = 50, name = "city")
	private String city;

	@Size(max = 50)
	@Column(length = 50, name = "street")
	private String street;

	@Size(max = 50)
	@Column(length = 50, name = "home_number")
	private String homeNumber;

	@Size(max = 50)
	@Column(length = 50, name = "flat_number")
	private String flatNumber;

	@OneToOne
	@JoinColumn(name = "juridical_person_id")
	private JuridicalPerson juridicalPerson;

	@NotNull
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<User> users = new HashSet<>();

	public Address() {}

	public JuridicalPerson getJuridicalPerson() {
		return juridicalPerson;
	}

	public void setJuridicalPerson(final JuridicalPerson juridicalPerson) {
		this.juridicalPerson = juridicalPerson;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(final String street) {
		this.street = street;
	}

	public String getHomeNumber() {
		return homeNumber;
	}

	public void setHomeNumber(final String homeNumber) {
		this.homeNumber = homeNumber;
	}

	public String getFlatNumber() {
		return flatNumber;
	}

	public void setFlatNumber(final String flatNumber) {
		this.flatNumber = flatNumber;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(final Set<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Address{" +
			"id=" + id +
			", city='" + city + '\'' +
			", street='" + street + '\'' +
			", homeNumber='" + homeNumber + '\'' +
			", flatNumber='" + flatNumber + '\'' +
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
		final Address address = (Address) o;
		return Objects.equals(id, address.id) &&
			Objects.equals(city, address.city) &&
			Objects.equals(street, address.street) &&
			Objects.equals(homeNumber, address.homeNumber) &&
			Objects.equals(flatNumber, address.flatNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, city, street, homeNumber, flatNumber);
	}
}
