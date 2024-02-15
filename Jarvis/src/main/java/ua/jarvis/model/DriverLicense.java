package ua.jarvis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
public class DriverLicense {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 50)
	@Column(length = 50, name = "name")
	private String name;

	@Size(max = 50)
	@Column(length = 50, name = "sur_name")
	private String surName;

	public DriverLicense() {}

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

	public String getSurName() {
		return surName;
	}

	public void setSurName(final String surName) {
		this.surName = surName;
	}

	@Override
	public String toString() {
		return "DriverLicense{" +
			"id=" + id +
			", name='" + name + '\'' +
			", surName='" + surName + '\'' +
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
		final DriverLicense that = (DriverLicense) o;
		return Objects.equals(id, that.id) &&
			Objects.equals(name, that.name) &&
			Objects.equals(surName, that.surName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, surName);
	}
}