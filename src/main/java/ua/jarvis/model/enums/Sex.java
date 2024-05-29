package ua.jarvis.model.enums;

public enum Sex {
	MALE("Male"),
	FEMALE("Female");

	private final String label;

	private Sex(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
