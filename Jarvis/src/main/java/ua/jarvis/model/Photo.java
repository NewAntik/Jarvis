package ua.jarvis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Entity
public class Photo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "UUID", length = 36, columnDefinition = "varchar")
	private UUID uuid;

	@NotBlank
	@Column(name = "file_name")
	@Size(max = 255)
	private String fileName;

	@OneToOne
	private User user;

	public Photo() {}

}
