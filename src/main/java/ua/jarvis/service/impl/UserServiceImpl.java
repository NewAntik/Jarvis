package ua.jarvis.service.impl;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;

import org.springframework.transaction.annotation.Transactional;
import ua.jarvis.model.User;
import ua.jarvis.repository.UserRepository;
import ua.jarvis.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	private final UserRepository userRepository;

	public UserServiceImpl(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public String getInfo() {
		return """
            Hello here will be some info about this bot.
            """;
	}

	@Override
	@Transactional(readOnly = true)
	public User findUserByPhoneNumber(final String phoneNumber) throws IOException {
		LOG.info("findUserByPhoneNumber method was called with phone number: {}", phoneNumber);

		final User user =userRepository.findByPhoneNumber(phoneNumber)
			.orElseThrow(() -> new IllegalArgumentException(
				"Данних повʼязаних з цим номером телефону: " + phoneNumber + " не існує!")
			);

		Hibernate.initialize(user.getPhones());
		Hibernate.initialize(user.getAddresses());

		LOG.info("In findUserByPhoneNumber method user was found with id: {}", user.getId());

		return user;
	}
}
