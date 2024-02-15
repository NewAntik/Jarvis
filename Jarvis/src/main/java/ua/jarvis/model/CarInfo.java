package ua.jarvis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;

import java.util.Objects;

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
	@Column(length = 50, name = "plate_number")
	private String model;

	@Size(max = 50)
	@Column(length = 50, name = "type")
	private String type;

	@ManyToOne
	private User user;

	public CarInfo() {}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(final String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getColor() {
		return color;
	}

	public void setColor(final String color) {
		this.color = color;
	}

	public String getModel() {
		return model;
	}

	public void setModel(final String model) {
		this.model = model;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "CarInfo{" +
			"id=" + id +
			", plateNumber='" + plateNumber + '\'' +
			", color='" + color + '\'' +
			", model='" + model + '\'' +
			", type='" + type + '\'' +
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
		final CarInfo carInfo = (CarInfo) o;
		return Objects.equals(id, carInfo.id) &&
			Objects.equals(plateNumber, carInfo.plateNumber) &&
			Objects.equals(color, carInfo.color) &&
			Objects.equals(model, carInfo.model) &&
			Objects.equals(type, carInfo.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, plateNumber, color, model, type);
	}
}
