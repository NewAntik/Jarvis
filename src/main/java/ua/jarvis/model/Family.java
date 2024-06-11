package ua.jarvis.model;

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
import ua.jarvis.model.enums.FamilyStatus;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "families")
public class Family {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 9)
	@Enumerated(EnumType.STRING)
	@Column(length = 9, name = "family_status")
	private FamilyStatus familyStatus;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "husband_id")
	private User husband;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "wife_id")
	private User wife;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "father_id")
	private User father;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "mother_id")
	private User mother;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "brother_id")
	private User brother;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "sister_id")
	private User sister;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(
		name = "family_children",
		joinColumns = @JoinColumn(name = "family_id"),
		inverseJoinColumns = @JoinColumn(name = "child_id")
	)
	private Set<User> children = new HashSet<>();

	public Family() {
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

	public User getFather() {
		return father;
	}

	public void setFather(final User father) {
		this.father = father;
	}

	public User getMother() {
		return mother;
	}

	public void setMother(final User mother) {
		this.mother = mother;
	}

	public User getBrother() {
		return brother;
	}

	public void setBrother(final User brother) {
		this.brother = brother;
	}

	public User getSister() {
		return sister;
	}

	public void setSister(final User sister) {
		this.sister = sister;
	}

	public Set<User> getChildren() {
		return children;
	}

	public void setChildren(final Set<User> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "Family{" +
			"id=" + id +
			", familyStatus=" + familyStatus +
			", husband=" + husband +
			", wife=" + wife +
			", father=" + father +
			", mother=" + mother +
			", brother=" + brother +
			", sister=" + sister +
			", children=" + children +
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
		final Family family = (Family) o;
		return familyStatus == family.familyStatus &&
			Objects.equals(husband, family.husband) &&
			Objects.equals(wife, family.wife) &&
			Objects.equals(father, family.father) &&
			Objects.equals(mother, family.mother) &&
			Objects.equals(brother, family.brother) &&
			Objects.equals(sister, family.sister) &&
			Objects.equals(children, family.children);
	}

	@Override
	public int hashCode() {
		return Objects.hash(familyStatus, husband, wife, father, mother, brother, sister, children);
	}
}
