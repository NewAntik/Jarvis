package ua.jarvis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class CarInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 20)
	@Column(length = 20, name = "plate_number")
	private String plateNumber;

	@Size(max = 20)
	@Column(length = 20, name = "color")
	private String color;

	@Size(max = 50)
	@Column(length = 50, name = "model")
	private String model;

	@Size(max = 50)
	@Column(length = 50, name = "type")
	private String type;//enum

	@Size(max = 17)
	@Column(length = 17, name = "vin_Code")
	private String vinCode;

	@Column(name = "date_release")
	private Date dateRelease;

	@ManyToMany
	private Set<User> user = new HashSet<>();

	@ManyToOne
	private JuridicalPerson juridicalPerson;

	public CarInfo() {}

}
