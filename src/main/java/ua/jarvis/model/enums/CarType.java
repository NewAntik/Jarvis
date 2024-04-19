package ua.jarvis.model.enums;

public enum CarType {
	SEDAN("Sedan"),
	SUV("SUV"),
	COUPE("Coupe"),
	CONVERTIBLE("Convertible"),
	HATCHBACK("Hatchback"),
	WAGON("Wagon"),
	MINIVAN("Minivan"),
	PICKUP("Pickup"),
	CROSSOVER("Crossover"),
	SUPERCAR("Supercar"),
	LIMOUSINE("Limousine"),
	CABRIOLET("Cabriolet"),
	TRUCK("Truck");

	private final String value;

	CarType(String value) {this.value = value;}

	public String getValue() {return value;}
}
