package ua.jarvis.service.impl;

import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;

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
	public File findUserByPhoneNumber(final String phoneNumber) throws IOException {
		final User user = userRepository.findByPhoneNumber(phoneNumber)
			.orElseThrow(() -> new IllegalArgumentException(
				"Данних повʼязаних з цим номером телефону: " + phoneNumber + " не існує!")
			);

		return pdfService.createUserPdf(user);
	}
}
