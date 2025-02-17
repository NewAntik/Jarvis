package ua.jarvis.core.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_sur_names")
public class SurName extends BaseNameEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public SurName() {
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
