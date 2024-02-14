package ua.jarvis.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class AddressInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 50)
	@Column(length = 50, name = "city")
	private String city;

	@Size(max = 50)
	@Column(length = 50, name = "street")
	private String street;

	@Size(max = 50)
	@Column(length = 50, name = "home_number")
	private String homeNumber;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<User> users= new HashSet<>();

	public AddressInfo() {}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(final Set<User> users) {
		this.users = users;
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

	@Override
	public String toString() {
		return "AddressInfo{" +
			"id=" + id +
			", city='" + city + '\'' +
			", street='" + street + '\'' +
			", homeNumber='" + homeNumber + '\'' +
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
		final AddressInfo that = (AddressInfo) o;
		return Objects.equals(id, that.id) &&
			Objects.equals(city, that.city) &&
			Objects.equals(street, that.street) &&
			Objects.equals(homeNumber, that.homeNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, city, street, homeNumber);
	}
}
