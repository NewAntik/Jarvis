package ua.jarvis.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
	@Column(length = 50, name = "region")
	private String region;

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

	@Size(max = 50)
	@Column(length = 50, name = "district")
	private String district;

	@Size(max = 50)
	@Column(length = 50, name = "corpus")
	private String corpus;

	@Size(max = 50)
	@Column(length = 50, name = "other")
	private String other;

	@Column(name = "other_num")
	private String otherNum;

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<JuridicalPerson> juridicalPersons = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<User> users = new HashSet<>();

	public Address() {}

	public String getRegion() {
		return region;
	}

	public void setRegion(final String region) {
		this.region = region;
	}

	public Set<JuridicalPerson> getJuridicalPersons() {
		return juridicalPersons;
	}

	public void setJuridicalPersons(final Set<JuridicalPerson> juridicalPersons) {
		this.juridicalPersons = juridicalPersons;
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

	public String getDistrict() {
		return district;
	}

	public void setDistrict(final String district) {
		this.district = district;
	}

	public String getCorpus() {
		return corpus;
	}

	public void setCorpus(final String corpus) {
		this.corpus = corpus;
	}

	public String getOther() {
		return other;
	}

	public void setOther(final String other) {
		this.other = other;
	}

	public String getOtherNum() {
		return otherNum;
	}

	public void setOtherNum(final String otherNum) {
		this.otherNum = otherNum;
	}

	@Override
	public String toString() {
		return "Address{" +
			"id=" + id +
			", region='" + region + '\'' +
			", city='" + city + '\'' +
			", street='" + street + '\'' +
			", homeNumber='" + homeNumber + '\'' +
			", flatNumber='" + flatNumber + '\'' +
			", district='" + district + '\'' +
			", corpus='" + corpus + '\'' +
			", other='" + other + '\'' +
			", otherNum=" + otherNum +
			", juridicalPersons=" + juridicalPersons +
			", users=" + users +
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
			Objects.equals(region, address.region) &&
			Objects.equals(city, address.city) &&
			Objects.equals(street, address.street) &&
			Objects.equals(homeNumber, address.homeNumber) &&
			Objects.equals(flatNumber, address.flatNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, region, city, street, homeNumber, flatNumber);
	}
}
