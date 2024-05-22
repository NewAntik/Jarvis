package ua.jarvis.service.impl;

import org.springframework.stereotype.Service;
import java.io.File;
import ua.jarvis.constant.Constants;
import ua.jarvis.model.User;
import ua.jarvis.repository.UserRepository;
import ua.jarvis.service.PdfService;
import ua.jarvis.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final PdfService pdfService;

	private final UserRepository userRepository;

	public UserServiceImpl(final PdfService pdfService, final UserRepository userRepository) {
		this.pdfService = pdfService;
		this.userRepository = userRepository;
	}

	@Override
	public String getInfo() {
		return """
            Hello here will be some info about this bot.
            """;

	}

	@Override
	public File findUserByPhoneNumber(final String messageText) {
		final String phoneNumber = normalizePhoneNumber(messageText);
		final User user = userRepository.findByPhoneNumber(phoneNumber)
			.orElseThrow(() -> new IllegalArgumentException("There is no user with phone number: " + phoneNumber));

		return pdfService.createUserPdf(user);
	}

	private String normalizePhoneNumber(final String phoneNumber) {
		String subNumber;
		if (phoneNumber.startsWith("+38")) {
			subNumber = phoneNumber.substring(3);
		}
		if (phoneNumber.startsWith("38")) {
			subNumber = phoneNumber.substring(2);
		}

		// Remove all non-digit characters
		subNumber = phoneNumber.replaceAll("[^\\d]", "");

		if(subNumber.length() != Constants.PHONE_NUMBER_LENGTH){
			throw new IllegalArgumentException("Invalid phone number.");
		}

		return subNumber;
	}
}
