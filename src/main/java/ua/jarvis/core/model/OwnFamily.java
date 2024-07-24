package ua.jarvis.core.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import ua.jarvis.core.model.enums.FamilyStatus;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "own_families")
public class OwnFamily {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 9)
	@Enumerated(EnumType.STRING)
	@Column(length = 9, name = "family_status")
	private FamilyStatus familyStatus;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "husband_id")
	private User husband;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "wife_id")
	private User wife;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
		name = "own_families_children",
		joinColumns = @JoinColumn(name = "family_id"),
		inverseJoinColumns = @JoinColumn(name = "child_id")
	)
	private Set<User> children = new HashSet<>();

	public OwnFamily() {
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public FamilyStatus getFamilyStatus() {
		return familyStatus;
	}

	public void setFamilyStatus(final FamilyStatus familyStatus) {
		this.familyStatus = familyStatus;
	}

	public User getHusband() {
		return husband;
	}

	public void setHusband(final User husband) {
		this.husband = husband;
	}

	public User getWife() {
		return wife;
	}

	public void setWife(final User wife) {
		this.wife = wife;
	}

	public Set<User> getChildren() {
		return children;
	}

	public void setChildren(final Set<User> children) {
		this.children = children;
	}

}
