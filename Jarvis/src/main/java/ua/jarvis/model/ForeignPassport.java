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
public class ForeignPassport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 8)
	@Column(length = 8, name = "passport_number")
	private String passportNumber;

	@Column(name = "date_issue")
	private Date dateIssue;

	@Column(name = "valid_until")
	private Date validUntil;

	@Column(name = "valid")
	private boolean valid;

	@Size(max = 200)
	@Column(length = 200, name = "authority")
	private String authority;

	@ManyToOne
	private User user;

	public ForeignPassport() {}

}
