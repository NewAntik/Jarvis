package ua.jarvis.core.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "driver_licenses")
public class DriverLicense extends DocumentEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 9)
	@Column(length = 9, name = "license_number")
	private String licenseNumber;

	@OneToMany(mappedBy = "driverLicense", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<DriverLicenseCategory> categories = new HashSet<>();

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;

	public DriverLicense() {}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(final String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public Set<DriverLicenseCategory> getCategories() {
		return categories;
	}

	public void setCategories(final Set<DriverLicenseCategory> categories) {
		this.categories = categories;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "DriverLicense{" +
			"id=" + id +
			", licenseNumber='" + licenseNumber + '\'' +
			", categories=" + categories +
			", user=" + user +
			", issueDate=" + issueDate +
			", validUntil=" + validUntil +
			", isValid=" + isValid +
			", authority='" + authority + '\'' +
			", isUnlimited=" + isUnlimited +
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
		return Objects.equals(licenseNumber, that.licenseNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(licenseNumber);
	}
}