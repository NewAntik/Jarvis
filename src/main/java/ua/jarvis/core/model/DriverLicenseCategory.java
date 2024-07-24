package ua.jarvis.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ua.jarvis.core.model.enums.CategoryType;

import java.util.Objects;

@Entity
@Table(name = "driver_license_categories")
public class DriverLicenseCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotBlank
	@Enumerated(EnumType.STRING)
	@Column(name = "category_type")
	private CategoryType categoryType;

	@ManyToOne
	private DriverLicense driverLicense;

	public DriverLicenseCategory() {}

	public DriverLicense getDriverLicense() {
		return driverLicense;
	}

	public void setDriverLicense(final DriverLicense driverLicense) {
		this.driverLicense = driverLicense;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public CategoryType getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(final CategoryType categoryType) {
		this.categoryType = categoryType;
	}

	@Override
	public String toString() {
		return "DriverLicenseCategory{" +
			"id=" + id +
			", categoryType=" + categoryType.getValue()+
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
		final DriverLicenseCategory that = (DriverLicenseCategory) o;
		return Objects.equals(id, that.id) && categoryType == that.categoryType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, categoryType);
	}
}
