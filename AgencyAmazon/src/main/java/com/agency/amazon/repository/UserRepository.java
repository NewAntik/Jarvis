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

	User findByLogin(String login);

	List<User> findByCreatedDate(Date createdDate);

	Optional<User> findByFirstName(String username);
	@Query("select u from User u where u.createdDate between ?1 and ?2")
	List<User> findBetweenDates(Date fromDate, Date toDate);

	List<User> findAllByCreatedDateIsNotNull();

	List<User> findAllByAsinIsNotNull();

	List<User> findAllByAsin(List<String> asins);

	User findByAsin(String asin);
}
