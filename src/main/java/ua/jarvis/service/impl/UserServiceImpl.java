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
           	Пошук за номером телефону(В будьякому форматі. Якщо 10 цифр то має починатись з 0(з нуля)).
           	Пошук за РНОКПП (не може починатись з 0 та бути меньше ніж 10 цифр).
            """;
	}

	@Override
	@Transactional(readOnly = true)
	public User findUserByForeignPassportNumber(final String foreignPassportNumber) {
		LOG.info("findUserByForeignPassportNumber method was called with foreign passport number: {}", foreignPassportNumber);

		final User user = userRepository.findUserByForeignPassportNumber(foreignPassportNumber).orElseThrow( () ->
			new IllegalArgumentException(
				"Данних повʼязаних з цим номером закордонного паспорту: " + foreignPassportNumber + " не існує!")
		);

		initialiseHibernateSessions(user);

		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public User findUserByPassportNumber(final String passportNumber) {
		LOG.info("findUserByPassportNumber method was called with passport number: {}", passportNumber);

		final User user = userRepository.findUserByPassportNumber(passportNumber).orElseThrow( () ->
			new IllegalArgumentException(
				"Данних повʼязаних з цим номером паспорту: " + passportNumber + " не існує!")
		);

		initialiseHibernateSessions(user);

		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findUsersByPhoneNumber(final String phoneNumber) {
		LOG.info("findUserByPhoneNumber method was called with phone number: {}", phoneNumber);

		final List<User> users = userRepository.findUsersByPhoneNumber(phoneNumber);
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
		Hibernate.initialize(user.getAddresses());
		Hibernate.initialize(user.getPassports());
		Hibernate.initialize(user.getForeignPassports());
		Hibernate.initialize(user.getDriverLicense());
		Hibernate.initialize(user.getCars());
		Hibernate.initialize(user.getEmails());
	}
}
