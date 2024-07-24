package ua.jarvis.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "photos")
public class Photo extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@JdbcTypeCode(SqlTypes.VARCHAR)
	@Column(name = "UUID", length = 36, columnDefinition = "varchar")
	private UUID uuid;

	@NotBlank
	@Column(name = "file_name")
	@Size(max = 255)
	private String fileName;

	@NotNull
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Photo() {}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(final UUID uuid) {
		this.uuid = uuid;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Photo{" +
			"id=" + id +
			", uuid=" + uuid +
			", fileName='" + fileName + '\'' +
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
		final Photo photo = (Photo) o;
		return Objects.equals(id, photo.id) &&
			Objects.equals(uuid, photo.uuid) &&
			Objects.equals(fileName, photo.fileName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, uuid, fileName);
	}
}
