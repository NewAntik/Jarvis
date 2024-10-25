package ua.jarvis.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ua.jarvis.core.model.enums.BooleanType;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class DocumentEntity extends BaseEntity {

	@Column(name = "issue_date", nullable = false, updatable = false)
	protected LocalDateTime issueDate;

	@Column(name = "valid_until", nullable = false)
	protected LocalDateTime validUntil;

	@Enumerated(EnumType.STRING)
	@Column(length = 7, name = "valid", nullable = false)
	protected BooleanType valid;

	@Size(max = 200)
	@Column(length = 200, name = "authority", nullable = false, updatable = false)
	protected String authority;

	@Enumerated(EnumType.STRING)
	@Column(length = 7, name = "unlimited", nullable = false)
	protected BooleanType unlimited;

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

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(final String authority) {
		this.authority = authority;
	}

	public BooleanType getValid() {
		return valid;
	}

	public void setValid(final BooleanType valid) {
		this.valid = valid;
	}

	public BooleanType getUnlimited() {
		return unlimited;
	}

	public void setUnlimited(final BooleanType isUnlimited) {
		this.unlimited = isUnlimited;
	}
}
