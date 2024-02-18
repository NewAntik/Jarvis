package ua.jarvis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;


@Entity
public class PhoneInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 50)
	@Column(length = 50, name = "phone_number")
	private String phoneNumber;

	@Size(max = 20)
	@Column(length = 20, name = "imei")
	private String imei;

	@ManyToOne
	private JuridicalPerson jurPerson;

	@ManyToOne
	private User user;

	public PhoneInfo() {}

}
