package ua.jarvis.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;
import ua.jarvis.core.model.JuridicalPerson;
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
	public List<User> findUsersByCriteria(final UserCriteria criteria) {
		LOG.info("findUsersByCriteria was called with criteria: {}", criteria);

		final Specification<User> spec = specProvider.get(criteria);

		final List<User> users = userRepository.findAll(spec);

		if(users.isEmpty()){
			throw new IllegalArgumentException(NOT_EXISTS);
		}

		if(users.size() == 1){
			initializeAllData(users);
		}else{
			initializeSortData(users);
		}

		return users;
	}

	private void initializeSortData(final List<User> users) {
		initBaseInfo(new HashSet<>(users));
	}

	private void initializeAllData(final List<User> users){
		for(final User user : users ){
			user.getCars().stream().count();
			user.getPassports().stream().count();
			user.getForeignPassports().stream().count();
			user.getJuridicalPersons().stream().count();
			user.getPhones().stream().count();
			user.getEmails().stream().count();
			user.getDriverLicense().stream().count();
			user.getAddresses().stream().count();
			user.getFirstNames().stream().count();
			user.getSurNames().stream().count();
			user.getMiddleNames().stream().count();
			user.getIndividualEntrepreneurAddresses().stream().count();

			if(!user.getChildren().isEmpty()){
				initBaseInfo(user.getChildren());
			}

			if(!user.getParents().isEmpty()){
				initBaseInfo(user.getParents());
			}

			if(!user.getSiblings().isEmpty()){
				initBaseInfo(user.getSiblings());
			}

			if(!user.getJuridicalPersons().isEmpty()){
				initJurPersonsInfo(user.getJuridicalPersons());
			}
		}
	}

	private void initJurPersonsInfo(final Set<JuridicalPerson> jurPersons){
		for(final JuridicalPerson jurPerson : jurPersons){
			jurPerson.getJurAddresses().stream().count();
		}
	}

	private void initBaseInfo(final Set<User> users){
		for(final User user : users){
			user.getPhones().stream().count();
			user.getFirstNames().stream().count();
			user.getSurNames().stream().count();
			user.getMiddleNames().stream().count();
		}
	}
}
