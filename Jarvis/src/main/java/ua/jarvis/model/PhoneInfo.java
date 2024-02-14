package ua.jarvis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;

import java.util.Objects;
import java.util.UUID;

@Entity
public class PhoneInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 50)
	@Column(length = 50, name = "model")
	private String model;

	@Size(max = 20)
	@Column(length = 20, name = "imei")
	private String imei;

	@ManyToOne
	private User user;

	public PhoneInfo() {}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(final String model) {
		this.model = model;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(final String imei) {
		this.imei = imei;
	}

	@Override
	public String toString() {
		return "PhoneInfo{" +
			"id=" + id +
			", model='" + model + '\'' +
			", imei='" + imei + '\'' +
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
		final PhoneInfo phoneInfo = (PhoneInfo) o;
		return Objects.equals(id, phoneInfo.id) &&
			Objects.equals(model, phoneInfo.model) &&
			Objects.equals(imei, phoneInfo.imei);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, model, imei);
	}
}
