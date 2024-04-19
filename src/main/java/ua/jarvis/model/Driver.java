package ua.jarvis.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ua.jarvis.model.enums.DriverType;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@DiscriminatorValue("Driver")
public class Driver extends User {

	@NotNull
	@Size(max = 10)
	@Enumerated(EnumType.STRING)
	@Column(length = 10, name = "driver_type")
	private DriverType type;

	@ManyToMany(mappedBy = "drivers", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Car> cars = new HashSet<>();

	public Driver() {
	}

	public DriverType getType() {
		return type;
	}

	public void setType(final DriverType type) {
		this.type = type;
	}

	public Set<Car> getCars() {
		return cars;
	}

	public void setCars(final Set<Car> cars) {
		this.cars = cars;
	}

	@Override
	public String toString() {
		return "Driver{" +
			"type=" + type +
			", cars=" + cars +
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
		if (!super.equals(o)) {
			return false;
		}
		final Driver driver = (Driver) o;
		return type == driver.type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), type);
	}
}
