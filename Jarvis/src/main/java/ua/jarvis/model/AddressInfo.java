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

	@Size(max = 50)
	@Column(length = 50, name = "flat_number")
	private String flatNumber;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<User> users = new HashSet<>();

	public AddressInfo() {}

}
