package ua.jarvis.service.impl;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import ua.jarvis.model.User;
import ua.jarvis.model.criteria.UserCriteria;
import ua.jarvis.model.specification.SpecificationProvider;
import ua.jarvis.repository.UserRepository;
import ua.jarvis.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
	public static final String NOT_EXISTS = " - не існує!";

	private final UserRepository userRepository;

	private final SpecificationProvider specProvider;

	public UserServiceImpl(
		final UserRepository userRepository,
		final SpecificationProvider specProvider
	) {
		this.userRepository = userRepository;
		this.specProvider = specProvider;
	}

	@Override
	@Transactional(readOnly = true)
	public User findUserByForeignPassportNumber(final String foreignPassportNumber) {
		LOG.info("findUserByForeignPassportNumber method was called with foreign passport number: {}", foreignPassportNumber);

		final User user = userRepository.findUserByForeignPassportNumber(foreignPassportNumber).orElseThrow( () ->
			new IllegalArgumentException(
				"Данних повʼязаних з цим номером закордонного паспорту: " + foreignPassportNumber + NOT_EXISTS)
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
				"Данних повʼязаних з цим ПІБ: " + surName + " " + name + " " + midlName + NOT_EXISTS)
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
				"Данних повʼязаних з цим прізвищем та імʼям: " + surName + " " + name + NOT_EXISTS)
		);

		initialiseHibernateSessions(user);

		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public User findUserBySurNameAndMidlName(UserCriteria criteria) {
		LOG.info("findUserBySurNameAndMiddleName method was called with criteria: {}", criteria);

		final Specification<User> spec = specProvider.get(criteria);

		return findBySpecification(spec).get(0);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findUserByThreeNamesAndDate(final UserCriteria criteria) {
		LOG.info("findUserByThreeNamesAndDate method was called with criteria: {}", criteria);
		final Specification<User> spec = specProvider.get(criteria);

		return findBySpecification(spec);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findUserByNameMidlNameAndDate(final UserCriteria criteria) {
		LOG.info("findUserByNameMidlNameAndDate method was called with criteria: {}", criteria);
		final Specification<User> spec = specProvider.get(criteria);

		return findBySpecification(spec);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findUserBySurNameMidlNameAndDate(final UserCriteria criteria) {
		LOG.info("findUserBySurNameMidlNameAndDate method was called with criteria: {}", criteria);
		final Specification<User> spec = specProvider.get(criteria);

		return findBySpecification(spec);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findUserBySurNameNameAndDate(final UserCriteria criteria) {
		LOG.info("findUserBySurNameNameAndDate method was called with criteria: {}", criteria);
		final Specification<User> spec = specProvider.get(criteria);

		return findBySpecification(spec);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findUserByThreeNamesDateAndRegion(final UserCriteria criteria) {
		LOG.info("findUserByThreeNamesDateAndRegion method was called with criteria: {}", criteria);
		final Specification<User> spec = specProvider.get(criteria);

		return findBySpecification(spec);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findUserBySurNameNameDateAndRegion(final UserCriteria criteria) {
		LOG.info("findUserBySurNameNameDateAndRegion method was called with criteria: {}", criteria);
		final Specification<User> spec = specProvider.get(criteria);

		return findBySpecification(spec);
	}

	private List<User> findBySpecification(final Specification<User> spec){
		final List<User> users = userRepository.findAll(spec);
		users.forEach(this::initialiseHibernateSessions);

		if(users.isEmpty()){
			throw new IllegalArgumentException(
				"Данних повʼязаних з цими данними: " + NOT_EXISTS);
		}

		return users;
	}


	@Override
	@Transactional(readOnly = true)
	public User findUserByPassportNumber(final String passportNumber) {
		LOG.info("findUserByPassportNumber method was called with passport number: {}", passportNumber);

		final User user = userRepository.findUserByPassportNumber(passportNumber).orElseThrow( () ->
			new IllegalArgumentException(
				"Данних повʼязаних з цим номером паспорту: " + passportNumber + NOT_EXISTS)
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
				"Данних повʼязаних з цим номером телефону: " + phoneNumber + NOT_EXISTS);
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
				"Данних повʼязаних з цим РНОКПП: " + rnokpp + NOT_EXISTS)
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
}
