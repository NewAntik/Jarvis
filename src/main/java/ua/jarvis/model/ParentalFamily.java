package ua.jarvis.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "parental_families")
public class ParentalFamily {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "father_id")
	private User father;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mother_id")
	private User mother;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "brother_id")
	private User brother;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sister_id")
	private User sister;

	public ParentalFamily() {}

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

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ParentalFamily{" +
			"id=" + id +
			", father=" + father +
			", mother=" + mother +
			", brother=" + brother +
			", sister=" + sister +
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
		final ParentalFamily that = (ParentalFamily) o;
		return Objects.equals(id, that.id) &&
			Objects.equals(father, that.father) &&
			Objects.equals(mother, that.mother) &&
			Objects.equals(brother, that.brother) &&
			Objects.equals(sister, that.sister);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, father, mother, brother, sister);
	}
}
