package com.agency.amazon.repository;

import com.agency.amazon.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	public User findByLogin(String login);
}
