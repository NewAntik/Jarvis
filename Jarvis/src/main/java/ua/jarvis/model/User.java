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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
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

	@Size(max = 10)
	@Column(length = 10, name = "sex")
	private String sex;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Email> emails = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<DriverLicense> driverLicense = new HashSet<>();

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Photo photo;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<PhoneInfo> phones = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<ForeignPassport> foreignPassports = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Passport> passports = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<CarInfo> cars = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<AddressInfo> addressInfos = new HashSet<>();

	public User() {}



}
