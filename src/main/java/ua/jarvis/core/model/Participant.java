package ua.jarvis.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ua.jarvis.core.model.enums.ParticipantRole;

import java.util.Objects;

@Entity
@Table(name = "participants")
public class Participant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotBlank
	@Size(max = 100)
	@Column(length = 100, name = "name")
	private String name;

	@NotNull
	@NotBlank
	@Size(max = 5)
	@Enumerated(EnumType.STRING)
	@Column(length = 5, name = "role")
	private ParticipantRole role;

	public Participant() {}

	public Participant(final Long id, final String name, final ParticipantRole role) {
		this.id = id;
		this.name = name;
		this.role = role;
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

	public void setName(final String username) {
		this.name = username;
	}

	public ParticipantRole getRole() {
		return role;
	}

	public void setRole(final ParticipantRole role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Participant{" +
			"id=" + id +
			", username='" + name + '\'' +
			", role=" + role +
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
		final Participant that = (Participant) o;
		return Objects.equals(id, that.id) &&
			Objects.equals(name, that.name) &&
			role == that.role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, role);
	}
}
