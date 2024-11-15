package ua.jarvis.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import ua.jarvis.core.model.enums.BooleanType;
import ua.jarvis.core.model.enums.Sex;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 10)
	@Column(length = 10, name = "rnokpp")
	private String rnokpp;

	@Enumerated(EnumType.STRING)
	@Column(length = 10, name = "sex")
	private Sex sex;

	@Size(max = 500)
	@Column(length = 500, name = "illegal_actions")
	private String illegalActions;

	@Enumerated(EnumType.STRING)
	@Column(length = 7, name = "individual_entrepreneur", nullable = false)
	private BooleanType individualEntrepreneur;

	@OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
	private BirthCertificate birthCertificate;

	@OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
	private Photo photo;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<FirstName> firstNames = new HashSet<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<MiddleName> middleNames = new HashSet<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<SurName> surNames = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "user_siblings",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "sibling_id")
	)
	private Set<User> siblings = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<User> children = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<User> parents = new HashSet<>();

	@ManyToMany(mappedBy = "drivers", fetch = FetchType.LAZY)
	private Set<Car> cars = new HashSet<>();

	@ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
	private Set<JuridicalPerson> juridicalPersons = new HashSet<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<Passport> passports = new HashSet<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<DriverLicense> driverLicense = new HashSet<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<ForeignPassport> foreignPassports = new HashSet<>();

	@ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
	private Set<Address> addresses = new HashSet<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<Email> emails = new HashSet<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<Phone> phones = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "individual_entrepreneur_addresses",
		joinColumns = @JoinColumn(name = "individual_entrepreneur_id"),
		inverseJoinColumns = @JoinColumn(name = "addresses_id")
	)
	private Set<Address> individualEntrepreneurAddresses = new HashSet<>();

	public User() {}

	public BooleanType getIndividualEntrepreneur() {
		return individualEntrepreneur;
	}

	public void setIndividualEntrepreneur(final BooleanType isIndividualEntrepreneur) {
		this.individualEntrepreneur = isIndividualEntrepreneur;
	}

	public Set<Address> getIndividualEntrepreneurAddresses() {
		return individualEntrepreneurAddresses;
	}

	public void setIndividualEntrepreneurAddresses(final Set<Address> isiIndividualEntrepreneurAddresses) {
		this.individualEntrepreneurAddresses = isiIndividualEntrepreneurAddresses;
	}

	public Set<User> getSiblings() {
		return siblings;
	}

	public void setSiblings(final Set<User> siblings) {
		this.siblings = siblings;
	}

	public Set<User> getChildren() {
		return children;
	}

	public void setChildren(final Set<User> children) {
		this.children = children;
	}

	public Set<User> getParents() {
		return parents;
	}

	public void setParents(final Set<User> parents) {
		this.parents = parents;
	}

	public Set<JuridicalPerson> getJuridicalPersons() {
		return juridicalPersons;
	}

	public void setJuridicalPersons(final Set<JuridicalPerson> juridicalPersons) {
		this.juridicalPersons = juridicalPersons;
	}

	public String getIllegalActions() {
		return illegalActions;
	}

	public void setIllegalActions(final String illegalActions) {
		this.illegalActions = illegalActions;
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

	public Set<FirstName> getFirstNames() {
		return firstNames;
	}

	public void setFirstNames(final Set<FirstName> firstNames) {
		this.firstNames = firstNames;
	}

	public Set<MiddleName> getMiddleNames() {
		return middleNames;
	}

	public void setMiddleNames(final Set<MiddleName> middleNames) {
		this.middleNames = middleNames;
	}

	public Set<SurName> getSurNames() {
		return surNames;
	}

	public void setSurNames(final Set<SurName> surNames) {
		this.surNames = surNames;
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

	public void addEmail(final Email email) {
		this.emails.add(email);
	}

	public void addPhone(final Phone phone) {
		this.phones.add(phone);
	}

	public void addAddress(final Address address) {
		this.addresses.add(address);
	}

	@Override
	public String toString() {
		return "User{" +
			"id=" + id +
			", names=" + firstNames +
			", middleNames=" + middleNames +
			", surNames=" + surNames +
			", rnokpp='" + rnokpp + '\'' +
			", sex=" + sex +
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
		return Objects.equals(rnokpp, user.rnokpp);
	}

	@Override
	public int hashCode() {
		return Objects.hash(rnokpp);
	}
}
