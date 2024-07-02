package ua.jarvis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ua.jarvis.model.enums.CarType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "cars")
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(max = 8)
	@Column(length = 8, name = "plate_number")
	private String plateNumber;

	@NotNull
	@Size(max = 50)
	@Column(length = 50, name = "color")
	private String color;

	@NotNull
	@Size(max = 50)
	@Column(length = 50, name = "model")
	private String model;

	@NotNull
	@Size(max = 100)
	@Enumerated(EnumType.STRING)
	@Column(length = 100, name = "car_type")
	private CarType type;

	@NotNull
	@Size(max = 17)
	@Column(length = 17, name = "vin_Code")
	private String vinCode;

	@NotNull
	@Column(name = "issue_date")
	private LocalDateTime issueDate;

	@ManyToMany
	private Set<User> drivers = new HashSet<>();

	@Column(name = "owner_id")
	private Long ownerId;

	@ManyToOne
	@JoinColumn(name = "juridical_person_id")
	private JuridicalPerson juridicalPerson;

	public Car() {}

	public void setCarOwner(final Long userId){
		this.ownerId = userId;
	}

	public Long getCarOwnerId(){
		return ownerId;
	}

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

	public CarType getType() {
		return type;
	}

	public void setType(final CarType type) {
		this.type = type;
	}

	public String getVinCode() {
		return vinCode;
	}

	public void setVinCode(final String vinCode) {
		this.vinCode = vinCode;
	}

	public LocalDateTime getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(final LocalDateTime issueDate) {
		this.issueDate = issueDate;
	}

	public Set<User> getDrivers() {
		return drivers;
	}

	public void setDrivers(final Set<User> driver) {
		this.drivers = driver;
	}

	public JuridicalPerson getJuridicalPerson() {
		return juridicalPerson;
	}

	public void setJuridicalPerson(final JuridicalPerson juridicalPerson) {
		this.juridicalPerson = juridicalPerson;
	}

	@Override
	public String toString() {
		return "Car{" +
			"id=" + id +
			", plateNumber='" + plateNumber + '\'' +
			", color='" + color + '\'' +
			", model='" + model + '\'' +
			", type=" + type +
			", vinCode='" + vinCode + '\'' +
			", dateRelease=" + issueDate +
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
		final Car car = (Car) o;
		return Objects.equals(id, car.id) &&
			Objects.equals(plateNumber, car.plateNumber) &&
			Objects.equals(color, car.color) &&
			Objects.equals(model, car.model) &&
			type == car.type &&
			Objects.equals(vinCode, car.vinCode) &&
			Objects.equals(issueDate, car.issueDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, plateNumber, color, model, type, vinCode, issueDate);
	}
}
