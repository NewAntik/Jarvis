package ua.jarvis.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 50)
	@Column(length = 50, name = "name")
	private String name;

	@Size(max = 50)
	@Column(length = 50, name = "midl_name")
	private String midlName;

	@Size(max = 50)
	@Column(length = 50, name = "sur_name")
	private String surName;

	@Size(max = 10)
	@Column(length = 10, name = "rnokpp")
	private String rnokpp;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private DriverLicense driverLicense;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Photo photo;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<PhoneInfo> phones = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<ForeignPassport> foreignPassports = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<CarInfo> cars = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<AddressInfo> addressInfos= new HashSet<>();

	public User() {}

	public Set<AddressInfo> getAddressInfos() {
		return addressInfos;
	}

	public void setAddressInfos(final Set<AddressInfo> addressInfos) {
		this.addressInfos = addressInfos;
	}

	public DriverLicense getDriverLicense() {
		return driverLicense;
	}

	public void setDriverLicense(final DriverLicense driverLicense) {
		this.driverLicense = driverLicense;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(final Photo photo) {
		this.photo = photo;
	}

	public Set<CarInfo> getCars() {
		return cars;
	}

	public void setCars(final Set<CarInfo> cars) {
		this.cars = cars;
	}

	public Set<ForeignPassport> getForeignPassports() {
		return foreignPassports;
	}

	public void setForeignPassports(final Set<ForeignPassport> foreignPassports) {
		this.foreignPassports = foreignPassports;
	}

	public Set<PhoneInfo> getPhones() {
		return phones;
	}

	public void setPhones(final Set<PhoneInfo> phones) {
		this.phones = phones;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getMidlName() {
		return midlName;
	}

	public void setMidlName(final String midlName) {
		this.midlName = midlName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(final String surName) {
		this.surName = surName;
	}

	public String getRnokpp() {
		return rnokpp;
	}

	public void setRnokpp(final String rnokpp) {
		this.rnokpp = rnokpp;
	}

	@Override
	public String toString() {
		return "User{" +
			"id=" + id +
			", name='" + name + '\'' +
			", midlName='" + midlName + '\'' +
			", surName='" + surName + '\'' +
			", rnokpp='" + rnokpp + '\'' +
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
		final User user = (User) o;
		return Objects.equals(id, user.id) &&
			Objects.equals(name, user.name) &&
			Objects.equals(midlName, user.midlName) &&
			Objects.equals(surName, user.surName) &&
			Objects.equals(rnokpp, user.rnokpp);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, midlName, surName, rnokpp);
	}
}
