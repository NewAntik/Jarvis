package ua.jarvis.service.impl;

import org.springframework.stereotype.Service;
import ua.jarvis.constant.Constants;
import ua.jarvis.service.PhoneService;

@Service
public class PhoneServiceImpl implements PhoneService {

	private String phoneNumber;

	public boolean isPhoneNumber(final String messageText) {
		phoneNumber = null;
		normalizePhoneNumber(messageText);

		return phoneNumber != null;
	}

	public String getNormalizedNumber(){
		return phoneNumber;
	}

	private void normalizePhoneNumber(String number) {
		number = number.replaceAll("[^\\d]", "");

		if (number.startsWith("38")) {
			number = number.substring(2);
		} else if (number.startsWith("8")) {
			number = number.substring(1);
		}

		if (number.length() == Constants.PHONE_NUMBER_LENGTH) {
			phoneNumber = number;
		}
	}
}
