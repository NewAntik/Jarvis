package ua.jarvis.model.enums;

public enum ParticipantRole {
	ADMIN("Admin"),

	STAFF("Staff");

	private final String value;

	ParticipantRole(String value) {this.value = value;}

	public String getValue() {return value;}
}
