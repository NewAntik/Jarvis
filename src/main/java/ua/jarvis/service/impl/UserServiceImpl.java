package ua.jarvis.service.impl;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

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
	public List<User> findUsersByPhoneNumber(final String phoneNumber) {
		LOG.info("findUserByPhoneNumber method was called with phone number: {}", phoneNumber);

		final List<User> users = userRepository.findByPhoneNumber(phoneNumber);
		if(users.isEmpty()){
			throw new IllegalArgumentException(
				"Данних повʼязаних з цим номером телефону: " + phoneNumber + " не існує!");
		}

		users.forEach(this::initialiseHibernateSessions);


		return users;
	}

	@Override
	@Transactional(readOnly = true)
	public User findUserByRnokpp(final String rnokpp) {
		LOG.info("findUserByRnokpp method was called with rnokpp: {}", rnokpp);

		final User user = userRepository.findUserByRnokpp(rnokpp).orElseThrow( () ->
			new IllegalArgumentException(
				"Данних повʼязаних з цим РНОКПП: " + rnokpp + " не існує!")
		);

		initialiseHibernateSessions(user);

		return user;
	}

	private void initialiseHibernateSessions(final User user) {
		Hibernate.initialize(user.getPhones());
		Hibernate.initialize(user.getPhones());
		Hibernate.initialize(user.getAddresses());
		Hibernate.initialize(user.getPassports());
		Hibernate.initialize(user.getForeignPassports());
		Hibernate.initialize(user.getDriverLicense());
		Hibernate.initialize(user.getCars());
		Hibernate.initialize(user.getEmails());
	}

	@Override
	public boolean isRnokpp(final String messageText){
		if (messageText == null) {
			return false;
		}
		if(messageText.length() != 10){
			return false;
		}
		if(messageText.startsWith("0")){
			return false;
		}

		return messageText.chars().allMatch(Character::isDigit);
	}
}
