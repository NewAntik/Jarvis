package com.agency.amazon.repository;

import com.agency.amazon.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	Optional<User> findByLogin(String login);

	Optional<List<User>> findByCreatedDate(Date createdDate);

	Optional<User> findByFirstName(String username);

	@Query("select u from User u where u.createdDate between ?1 and ?2")
	Optional<List<User>>findBetweenDates(Date fromDate, Date toDate);

	Optional<List<User>> findAllByCreatedDateIsNotNull();

	Optional<List<User>> findAllByAsinIsNotNull();

	Optional<List<User>> findAllByAsinIn(List<String> asins);

	Optional<User>findByAsin(String asin);

	Optional<List<User>> findAllByCreatedDateIn(List<Date> dates);
}
