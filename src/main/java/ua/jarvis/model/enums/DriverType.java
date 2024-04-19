package ua.jarvis.model.enums;

public enum DriverType {
	OWNER("Owner"),

	DRIVER("Driver");

	private final String value;

	DriverType(String value) {this.value = value;}

	public String getValue() {return value;}
}
