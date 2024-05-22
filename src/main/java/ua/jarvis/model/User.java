package ua.jarvis.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import ua.jarvis.model.enums.DriverType;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

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

	@Size(max = 10)
	@Column(length = 10, name = "sex")
	private String sex;

	@Size(max = 10)
	@Enumerated(EnumType.STRING)
	@Column(length = 10, name = "driver_type")
	private DriverType type;

	@ManyToMany(mappedBy = "drivers", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Car> cars = new HashSet<>();

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private JuridicalPerson juridicalPerson;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Passport> passports = new HashSet<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<DriverLicense> driverLicense = new HashSet<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<ForeignPassport> foreignPassports = new HashSet<>();

	@ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
	private Set<Address> addresses = new HashSet<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Email> emails = new HashSet<>();

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Photo photo;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Phone> phones = new HashSet<>();

	public User() {}

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

	public String getSex() {
		return sex;
	}

	public void setSex(final String sex) {
		this.sex = sex;
	}

	public JuridicalPerson getJuridicalPerson() {
		return juridicalPerson;
	}

	public void setJuridicalPerson(final JuridicalPerson juridicalPerson) {
		this.juridicalPerson = juridicalPerson;
	}

	public Set<Email> getEmails() {
		return emails;
	}

	public void setEmails(final Set<Email> emails) {
		this.emails = emails;
	}

	public Set<DriverLicense> getDriverLicense() {
		return driverLicense;
	}

	public void setDriverLicense(final Set<DriverLicense> driverLicense) {
		this.driverLicense = driverLicense;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(final Photo photo) {
		this.photo = photo;
	}

	public Set<Phone> getPhones() {
		return phones;
	}


	public void setPhones(final Set<Phone> phones) {
		this.phones = phones;
	}

	public Set<ForeignPassport> getForeignPassports() {
		return foreignPassports;
	}

	public void setForeignPassports(final Set<ForeignPassport> foreignPassports) {
		this.foreignPassports = foreignPassports;
	}

	public Set<Passport> getPassports() {
		return passports;
	}

	public void setPassports(final Set<Passport> passports) {
		this.passports = passports;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(final Set<Address> addresses) {
		this.addresses = addresses;
	}

	public DriverType getType() {
		return type;
	}

	public void setType(final DriverType type) {
		this.type = type;
	}

	public Set<Car> getCars() {
		return cars;
	}

	public void setCars(final Set<Car> cars) {
		this.cars = cars;
	}

	@Override
	public String toString() {
		return "User{" +
			"id=" + id +
			", name='" + name + '\'' +
			", midlName='" + midlName + '\'' +
			", surName='" + surName + '\'' +
			", rnokpp='" + rnokpp + '\'' +
			", sex='" + sex + '\'' +
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
			Objects.equals(rnokpp, user.rnokpp) &&
			Objects.equals(sex, user.sex);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, midlName, surName, rnokpp, sex);
	}
}
