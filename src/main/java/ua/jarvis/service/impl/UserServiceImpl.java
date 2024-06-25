package ua.jarvis.service.impl;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import ua.jarvis.model.BirthCertificate;
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
	@Transactional(readOnly = true)
	public User findUserByForeignPassportNumber(final String foreignPassportNumber) {
		LOG.info("findUserByForeignPassportNumber method was called with foreign passport number: {}", foreignPassportNumber);

		final User user = userRepository.findUserByForeignPassportNumber(foreignPassportNumber).orElseThrow( () ->
			new IllegalArgumentException(
				"Данних повʼязаних з цим номером закордонного паспорту: " + foreignPassportNumber + " - не існує!")
		);

		initialiseHibernateSessions(user);

		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public User findUserByThreeNames(final String surName, final String name, final String midlName) {
		LOG.info("findUserByThreeNames method was called with names: {}, {}, {}", surName, name, midlName);

		final User user = userRepository.findUserByThreeNames(surName, name, midlName).orElseThrow( () ->
			new IllegalArgumentException(
				"Данних повʼязаних з цим ПІБ: " + surName + " " + name + " " + midlName + " - не існує!")
		);

		initialiseHibernateSessions(user);

		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public User findUserBySurNameAndName(final String surName, final String name) {
		LOG.info("findUserBySurNameAndName method was called with names: {}, {}", surName, name);

		final User user = userRepository.findUserBySurNameAndName(surName, name).orElseThrow( () ->
			new IllegalArgumentException(
				"Данних повʼязаних з цим прізвищем та імʼям: " + surName + " " + name + " - не існує!")
		);

		initialiseHibernateSessions(user);

		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public User findUserBySurNameAndMidlName(final String surName, final String midlName) {
		LOG.info("findUserBySurNameAndMidlName method was called with names: {}, {}", surName, midlName);

		final User user = userRepository.findUserBySurNameAndMidlName(surName, midlName).orElseThrow( () ->
			new IllegalArgumentException(
				"Данних повʼязаних з цим прізвищем та по батькові: " + surName + " " + midlName + " - не існує!")
		);

		initialiseHibernateSessions(user);

		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findUserByThreeNamesAndDate(
		final String surName,
		final String name,
		final String midlName,
		final String day,
		final String month,
		final String year
	) {
		LOG.info("findUserByThreeNamesAndDate method was called with names and date: {}, {}, {}, {}{}{}",
			surName, name, midlName, day, month, year
		);

		List<User> users;
		if(day.equals("00") && !month.equals("00") && !year.equals("0000")){
			users = findUserByThreeNamesAndMonthYear(surName, name, midlName, month, year);
		} else if(month.equals("00")&& !day.equals("00") && !year.equals("0000")){
			users = findUserByThreeNamesAndDayYear(surName, name, midlName, day, year);
		} else if(year.equals("0000") && !month.equals("00")&& !day.equals("00")){
			users = findUserByThreeNamesAndDayMonth(surName, name, midlName, day, month);
		} else {
			users = findUserByThreeNamesAndFullData(surName, name, midlName, day, month, year);
		}
		if(users.isEmpty()) {
			throw new IllegalArgumentException("Не корректний формат.");
		}

		return users;
	}

	private List<User> findUserByThreeNamesAndFullData(
		final String surName,
		final String name,
		final String midlName,
		final String day,
		final String month,
		final String year

	) {
		LOG.info("findUserByThreeNamesAndFullData method was called with names and date: {}, {}, {}, {}, {}, {}",
			surName, name, midlName, day, month, year
		);
		final Specification<User> spec =
			Specification.where(UserSpecification.hasSurName(surName))
				.and(UserSpecification.hasName(name))
				.and(UserSpecification.hasMidlName(midlName))
				.and(UserSpecification.hasBirthCertificateDay(day))
				.and(UserSpecification.hasBirthCertificateMonth(month))
				.and(UserSpecification.hasBirthCertificateYear(year));

		final List<User> users = userRepository.findAll(spec);
		users.forEach(this::initialiseHibernateSessions);

		if(users.isEmpty()){
			throw new IllegalArgumentException(
				"Данних повʼязаних з цим ПІБ та датою: " +
					surName + " " + name + " " + midlName + " " + day + "." + month + "." + year + " - не існує!");
		}

		return users;
	}

	private List<User> findUserByThreeNamesAndDayMonth(
		final String surName,
		final String name,
		final String midlName,
		final String day,
		final String month
	) {
		LOG.info("findUserByThreeNamesAndDayMonth method was called with names and date: {}, {}, {}, {}{}",
			surName, name, midlName, day, month
		);
		final Specification<User> spec = Specification.where(UserSpecification.hasSurName(surName))
			.and(UserSpecification.hasName(name))
			.and(UserSpecification.hasMidlName(midlName))
			.and(UserSpecification.hasBirthCertificateDay(day))
			.and(UserSpecification.hasBirthCertificateMonth(month));

		final List<User> users = userRepository.findAll(spec);
		users.forEach(this::initialiseHibernateSessions);

		if(users.isEmpty()){
			throw new IllegalArgumentException(
				"Данних повʼязаних з цим ПІБ та датою: " +
					surName + " " + name + " " + midlName + " " +  day + "." + month + "0000" + " - не існує!");
		}

		return users;
	}

	private List<User> findUserByThreeNamesAndDayYear(
		final String surName,
		final String name,
		final String midlName,
		final String day,
		final String year
	) {
		LOG.info("findUserByThreeNamesAndDayYear method was called with names and date: {}, {}, {}, {}, {}",
			surName, name, midlName, day, year
		);

		final Specification<User> spec = Specification.where(UserSpecification.hasSurName(surName))
			.and(UserSpecification.hasName(name))
			.and(UserSpecification.hasMidlName(midlName))
			.and(UserSpecification.hasBirthCertificateDay(day))
			.and(UserSpecification.hasBirthCertificateYear(year));

		final List<User> users = userRepository.findAll(spec);
		users.forEach(this::initialiseHibernateSessions);

		if(users.isEmpty()){
			throw new IllegalArgumentException(
				"Данних повʼязаних з цим ПІБ та датою: " +
					surName + " " + name + " " + midlName + " " + day + ".00." + year + " - не існує!");
		}

		return users;
	}

	private List<User> findUserByThreeNamesAndMonthYear(
		final String surName,
		final String name,
		final String midlName,
		final String month,
		final String year
	){
		Specification<User> spec = Specification.where(UserSpecification.hasSurName(surName))
			.and(UserSpecification.hasName(name))
			.and(UserSpecification.hasMidlName(midlName))
			.and(UserSpecification.hasBirthCertificateMonth(month))
			.and(UserSpecification.hasBirthCertificateYear(year));

		final List<User> users = userRepository.findAll(spec);
		users.forEach(this::initialiseHibernateSessions);

		if(users.isEmpty()){
			throw new IllegalArgumentException(
				"Данних повʼязаних з цим ПІБ та датою: " +
					surName + " " + name + " " + midlName + " " + "00." + month + "." + year + " - не існує!");
		}

		return users;
	}

	@Override
	@Transactional(readOnly = true)
	public User findUserByPassportNumber(final String passportNumber) {
		LOG.info("findUserByPassportNumber method was called with passport number: {}", passportNumber);

		final User user = userRepository.findUserByPassportNumber(passportNumber).orElseThrow( () ->
			new IllegalArgumentException(
				"Данних повʼязаних з цим номером паспорту: " + passportNumber + " - не існує!")
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
				"Данних повʼязаних з цим номером телефону: " + phoneNumber + " - не існує!");
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
				"Данних повʼязаних з цим РНОКПП: " + rnokpp + " - не існує!")
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
		Hibernate.initialize(user.getBirthCertificate());
	}

	private static class UserSpecification{

		public static Specification<User> hasSurName(String surName) {
			return (root, query, builder) -> builder.equal(root.get("surName"), surName);
		}

		public static Specification<User> hasName(String name) {
			return (root, query, builder) -> builder.equal(root.get("name"), name);
		}

		public static Specification<User> hasMidlName(String midlName) {
			return (root, query, builder) -> builder.equal(root.get("midlName"), midlName);
		}

		public static Specification<User> hasBirthCertificateMonth(String month) {
			return (root, query, builder) -> {
				Join<User, BirthCertificate> birthCertificateJoin = root.join("birthCertificate", JoinType.INNER);
					return builder.equal(birthCertificateJoin.get("month"), month);
			};
		}

		public static Specification<User> hasBirthCertificateYear(String year) {
			return (root, query, builder) -> {
				Join<User, BirthCertificate> birthCertificateJoin = root.join("birthCertificate", JoinType.INNER);
				return builder.equal(birthCertificateJoin.get("year"), year);
			};
		}

		public static Specification<User> hasBirthCertificateDay(String day) {
			return (root, query, builder) -> {
				Join<User, BirthCertificate> birthCertificateJoin = root.join("birthCertificate", JoinType.INNER);
				return builder.equal(birthCertificateJoin.get("day"), day);
			};
		}
	}
}
