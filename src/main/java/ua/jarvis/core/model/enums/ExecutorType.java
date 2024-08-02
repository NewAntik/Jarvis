package ua.jarvis.core.model.enums;

public enum ExecutorType {
	INFO("Info"),
	RNOKPP("Rnokpp"),
	PHONE_NUMBER("Phone number"),
	PASSPORT("Passport"),
	FOREIGN_PASSPORT("Foreign passport"),
	SURNAME_AND_NAME("Sur name and name"),
	SURNAME_AND_MIDL_NAME("Sur name and midl name"),
	NAME_SURNAME_MIDL_NAME_DATE("Name, sur name, midl name, date"),
	UNDERSCORE_NAME_MIDL_NAME_DATE("_, name, midl name, date"),
	SURNAME_UNDERSCORE_MIDL_NAME_DATE("Sur name, _, midl name, date"),
	SURNAME_NAME_UNDERSCORE_DATE("Name, sur name, _, date"),
	THREE_NAMES_DATE_REGION("Three names, date, region"),
	SURNAME_NAME_UNDERSCORE_DATE_REGION("Sur name, name, _, date, region"),
	CAR_PLATE_NUMBER("Car plate number"),
	NAME_SUR_NAME_MIDL_NAME("Name, sur name, midl name"),
	THREE_NAMES_AND_REGION("Three names and region"),
	SURNAME_NAME_AND_REGION("Surname, name and region"),
	ADDRESS("Address");

	private final String value;

	ExecutorType(final String value) {
		this.value = value;
	}
	public String getValue() {return value;}
}
