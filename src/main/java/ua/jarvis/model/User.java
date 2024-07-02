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
import ua.jarvis.model.enums.Sex;

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
	@Column(length = 50, name = "middle_name")
	private String middleName;

	@Size(max = 50)
	@Column(length = 50, name = "sur_name")
	private String surName;

	@Size(max = 10)
	@Column(length = 10, name = "rnokpp")
	private String rnokpp;

	@Size(max = 10)
	@Enumerated(EnumType.STRING)
	@Column(length = 10, name = "sex")
	private Sex sex;

	@Size(max = 500)
	@Column(length = 500, name = "illegal_actions")
	private String illegalActions;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private BirthCertificate birthCertificate;

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Family> families = new HashSet<>();

	@ManyToMany(mappedBy = "drivers", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Car> cars = new HashSet<>();

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private JuridicalPerson juridicalPerson;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Passport> passports = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<DriverLicense> driverLicense = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<ForeignPassport> foreignPassports = new HashSet<>();

	@ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
	private Set<Address> addresses = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Email> emails = new HashSet<>();

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Photo photo;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Phone> phones = new HashSet<>();

	public User() {}

	public String getIllegalActions() {
		return illegalActions;
	}

	public void setIllegalActions(final String illegalActions) {
		this.illegalActions = illegalActions;
	}

	public Set<Family> getFamilies() {
		return families;
	}

	public void setFamilies(final Set<Family> families) {
		this.families = families;
	}

	public BirthCertificate getBirthCertificate() {
		return birthCertificate;
	}

	public void setBirthCertificate(final BirthCertificate birthCertificate) {
		this.birthCertificate = birthCertificate;
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

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(final String midlName) {
		this.middleName = midlName;
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

	public Sex getSex() {return sex;}

	public void setSex(final Sex sex) {
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
			", midlName='" + middleName + '\'' +
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
			Objects.equals(middleName, user.middleName) &&
			Objects.equals(surName, user.surName) &&
			Objects.equals(rnokpp, user.rnokpp) &&
			Objects.equals(sex, user.sex);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, middleName, surName, rnokpp, sex);
	}
}
