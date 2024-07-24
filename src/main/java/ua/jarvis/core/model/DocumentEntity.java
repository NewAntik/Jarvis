package ua.jarvis.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class DocumentEntity {

	@Column(name = "issue_date", nullable = false, updatable = false)
	protected LocalDateTime issueDate;

	@Column(name = "valid_until", nullable = false)
	protected LocalDateTime validUntil;

	@Column(name = "is_valid", nullable = false)
	protected boolean isValid;

	@Size(max = 200)
	@Column(length = 200, name = "authority", nullable = false, updatable = false)
	protected String authority;

	@Column(name = "is_unlimited")
	protected boolean isUnlimited;

	public boolean isUnlimited() {
		return isUnlimited;
	}

	public void setUnlimited(final boolean unlimited) {
		isUnlimited = unlimited;
	}

	public LocalDateTime getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(final LocalDateTime issueDate) {
		this.issueDate = issueDate;
	}

	public LocalDateTime getValidUntil() {
		return validUntil;
	}

	public void setValidUntil(final LocalDateTime validUntil) {
		this.validUntil = validUntil;
	}

	public boolean isValidity() {
		return isValid;
	}

	public void setValidity(final boolean isValid) {
		this.isValid = isValid;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(final String authority) {
		this.authority = authority;
	}
}
