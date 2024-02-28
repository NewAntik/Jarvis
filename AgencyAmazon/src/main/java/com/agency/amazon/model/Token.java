package com.agency.amazon.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tokens")
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@Size(max = 1000)
	private String tokenValue;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "expiration_date")
	private LocalDateTime expirationDate;

	@Column
	private boolean used;

	@CreatedDate
	@Column(name = "created_date", nullable = false, updatable = false)
	protected LocalDateTime createdDate;

	@LastModifiedDate
	@Column(name = "updated_date", nullable = false)
	protected LocalDateTime updatedDate;

	public Token() {}

	public Token(final String tokenValue, final User user, final LocalDateTime tokenExpirationDate) {
		this.tokenValue = tokenValue;
		this.user = user;
		this.expirationDate = tokenExpirationDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getTokenValue() {
		return tokenValue;
	}

	public void setTokenValue(final String tokenValue) {
		this.tokenValue = tokenValue;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(final LocalDateTime expirationDate) {
		this.expirationDate = expirationDate;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(final boolean used) {
		this.used = used;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(final LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(final LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public String toString() {
		return "Token{" +
			"id=" + id +
			", tokenValue='" + tokenValue + '\'' +
			", expirationDate=" + expirationDate +
			", used=" + used +
			", createdDate=" + createdDate +
			", updatedDate=" + updatedDate +
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
		final Token token = (Token) o;
		return used == token.used &&
			Objects.equals(id, token.id) &&
			Objects.equals(tokenValue, token.tokenValue) &&
			Objects.equals(expirationDate, token.expirationDate) &&
			Objects.equals(createdDate, token.createdDate) &&
			Objects.equals(updatedDate, token.updatedDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, tokenValue, expirationDate, used, createdDate, updatedDate);
	}
}
