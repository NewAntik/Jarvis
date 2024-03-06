package ua.jarvis.model.enums;

public enum CategoryType {

	A("A"),
	A1("A1"),
	B("B"),
	B1("B1"),
	BE("BE"),
	C("C"),
	C1("C1"),
	CE("CE"),
	C1E("C1E"),
	D("D"),
	D1("D1"),
	DE("DE"),
	D1E("D1E"),
	T1("T1"),
	T2("T2");

	private final String value;

	CategoryType(String value) {this.value = value;}

	public String getValue() {return value;}
}
