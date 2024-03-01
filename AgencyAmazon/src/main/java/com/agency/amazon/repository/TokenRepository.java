package com.agency.amazon.repository;

import com.agency.amazon.model.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {
	Optional<Token> findByValue(final String value);

	Optional<Token> findByUserId(final String id);
}
