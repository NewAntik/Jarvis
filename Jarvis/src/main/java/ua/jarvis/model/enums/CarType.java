package ua.jarvis.model.enums;

public enum CarType {
	Sedan("Sedan"),
	SUV("SUV"),
	Coupe("Coupe"),
	Convertible("Convertible"),
	Hatchback("Hatchback"),
	Wagon("Wagon"),
	Minivan("Minivan"),
	Pickup("Pickup"),
	Crossover("Crossover"),
	Supercar("Supercar"),
	Limousine("Limousine");

	private final String value;

	CarType(String value) {this.value = value;}

	public String getValue() {return value;}
}
