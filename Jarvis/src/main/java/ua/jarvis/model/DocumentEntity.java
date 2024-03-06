package ua.jarvis.model;

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

	@Column(name = "validity", nullable = false)
	protected boolean validity;

	@Size(max = 200)
	@Column(length = 200, name = "authority", nullable = false, updatable = false)
	protected String authority;

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
		return validity;
	}

	public void setValidity(final boolean validity) {
		this.validity = validity;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(final String authority) {
		this.authority = authority;
	}
}
