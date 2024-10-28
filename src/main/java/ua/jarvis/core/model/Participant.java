package ua.jarvis.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ua.jarvis.core.model.enums.ParticipantRole;

import java.util.Objects;

@Entity
@Table(name = "participants")
public class Participant extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "telegram_id", unique = true, nullable = false)
	private Long telegramId;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private ParticipantRole role;

	public Participant() {}

	public Participant(final Long telegramId, final ParticipantRole role) {
		this.telegramId = telegramId;
		this.role = role;
	}

	public Long getTelegramId() {
		return telegramId;
	}

	public void setTelegramId(final Long telegramId) {
		this.telegramId = telegramId;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
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
			", telegramId=" + telegramId +
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
		return Objects.equals(telegramId, that.telegramId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(telegramId);
	}
}
