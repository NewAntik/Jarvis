package ua.jarvis.service;

public interface PhoneService {
	boolean isPhoneNumber(String message);

	String getNormalizedNumber();
}
