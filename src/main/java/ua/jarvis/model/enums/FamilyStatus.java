package ua.jarvis.model.enums;

public enum FamilyStatus {
	WIDOWER("Widower"),
	DIVORCED("Divorced"),
	UNMARRIED("Unmarried"),
	MARRIED("Married");

	private final String value;

	FamilyStatus(String value) {this.value = value;}

	public String getValue() {return value;}
}
