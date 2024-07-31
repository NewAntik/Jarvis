package ua.jarvis.service.impl;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;
import ua.jarvis.core.model.OwnFamily;
import ua.jarvis.core.model.ParentalFamily;
import ua.jarvis.core.model.User;
import ua.jarvis.core.model.criteria.UserCriteria;
import ua.jarvis.core.model.specification.SpecificationProvider;
import ua.jarvis.repository.UserRepository;
import ua.jarvis.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
	public static final String NOT_EXISTS = " Нічого не знайдено.";

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

	@Override
	@Transactional(readOnly = true)
	public List<User> findUserByCarPlateNumber(final UserCriteria criteria) {
		LOG.info("findUserByCarPlateNumber method was called with criteria: {}", criteria);
		final Specification<User> spec = specProvider.get(criteria);

		return findBySpecification(spec);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findUsersByCriteria(final UserCriteria criteria) {
		final Specification<User> spec = specProvider.get(criteria);

		final List<User> users = userRepository.findAll(spec);

		if(users.isEmpty()){
			throw new IllegalArgumentException(NOT_EXISTS);
		}

		users.forEach(this::initialiseHibernateSessions);

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

	private List<User> findBySpecification(final Specification<User> spec){
		final List<User> users = userRepository.findAll(spec);

		if(users.isEmpty()){
			throw new IllegalArgumentException(NOT_EXISTS);
		}

		users.forEach(this::initialiseHibernateSessions);

		return users;
	}

	private void initialiseHibernateSessions(final User user) {
		Hibernate.initialize(user.getJuridicalPersons());
		Hibernate.initialize(user.getPhones());
		Hibernate.initialize(user.getAddresses());
		Hibernate.initialize(user.getPassports());
		Hibernate.initialize(user.getForeignPassports());
		Hibernate.initialize(user.getDriverLicense());
		Hibernate.initialize(user.getCars());
		Hibernate.initialize(user.getEmails());
		Hibernate.initialize(user.getBirthCertificate());
		Hibernate.initialize(user.getOwnFamilies());
		Hibernate.initialize(user.getParentalFamily());
		initialiseFamilies(user);
	}

	private void initialiseFamilies(final User user) {
		final Set<OwnFamily> ownFamilySet = user.getOwnFamilies();
		if(!ownFamilySet.isEmpty()){
			initialiseHusband(ownFamilySet);
			initialiseWife(ownFamilySet);
			initialiseWifesPhones(ownFamilySet);
			initialiseHusbandsPhones(ownFamilySet);
			initialiseChildren(ownFamilySet);
		}
		if(user.getParentalFamily() != null){
			initialiseParentalFamily(user.getParentalFamily());
		}
	}

	private void initialiseHusband(final Set<OwnFamily> ownFamily){
		ownFamily.forEach(f -> {
			if (f.getHusband() != null) {
				Hibernate.initialize(f.getHusband());
			}
		});
	}

	private void initialiseWife(final Set<OwnFamily> ownFamilySet) {
		ownFamilySet.forEach(f -> {
			if (f.getWife() != null) {
				Hibernate.initialize(f.getWife());
			}
		});
	}

	private void initialiseWifesPhones(final Set<OwnFamily> ownFamilySet) {
		ownFamilySet.forEach(f ->{
			if (f.getWife() != null && !f.getWife().getPhones().isEmpty()) {
				Hibernate.initialize(f.getWife().getPhones());
			}
		});
	}

	private void initialiseHusbandsPhones(final Set<OwnFamily> ownFamilySet) {
		ownFamilySet.forEach(f -> {
			if (f.getHusband() != null && !f.getHusband().getPhones().isEmpty()) {
				Hibernate.initialize(f.getHusband().getPhones());
			}
		});
	}

	private void initialiseChildren(final Set<OwnFamily> ownFamilySet) {
		ownFamilySet.forEach(f ->{
			if (!f.getChildren().isEmpty()) {
				f.getChildren().forEach(this::initialise);
			}
		});
	}

	private void initialiseParentalFamily(final ParentalFamily parentalFamily) {
		if(parentalFamily.getBrother() != null){
			Hibernate.initialize(parentalFamily.getBrother());
		}
		if(!parentalFamily.getBrother().getPhones().isEmpty()){
			Hibernate.initialize(parentalFamily.getBrother().getPhones());
		}
		if(parentalFamily.getSister() != null){
			Hibernate.initialize(parentalFamily.getSister());
		}
		if(!parentalFamily.getSister().getPhones().isEmpty()){
			Hibernate.initialize(parentalFamily.getSister().getPhones());
		}
		if(parentalFamily.getMother() != null){
			Hibernate.initialize(parentalFamily.getMother());
		}
		if(!parentalFamily.getMother().getPhones().isEmpty()){
			Hibernate.initialize(parentalFamily.getMother().getPhones());
		}
		if(parentalFamily.getFather() != null){
			Hibernate.initialize(parentalFamily.getFather());
		}
		if(!parentalFamily.getFather().getPhones().isEmpty()){
			Hibernate.initialize(parentalFamily.getFather().getPhones());
		}
	}

	private void initialise(final User user) {
		Hibernate.initialize(user.getPhones());
	}
}
