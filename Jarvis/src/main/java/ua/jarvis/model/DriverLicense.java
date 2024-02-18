package ua.jarvis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
public class DriverLicense {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "date_issue")
	private Date dateIssue;

	@Column(name = "valid_until")
	private Date validUntil;

	@Column(name = "valid")
	private boolean valid;

	@Size(max = 200)
	@Column(length = 200, name = "authority")
	private String authority;

	@Size(max = 9)
	@Column(length = 9, name = "license_number")
	private String licenseNumber;

	@Column(name = "categories")
	private String categories; // перечисление "A, B, C, C1,"

	@ManyToOne
	private User user;

	public DriverLicense() {}

}