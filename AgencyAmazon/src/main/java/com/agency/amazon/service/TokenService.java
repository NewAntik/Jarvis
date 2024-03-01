package com.agency.amazon.service;

import com.agency.amazon.model.Token;
import com.agency.amazon.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface TokenService {
	@Transactional()
	Token generateToken(User user);

	String getUserName(String tokenHeader);

	boolean validateToken(final Token token, final User user);

	@Transactional(readOnly = true)
	Token findByValue(String jwtToken);

	@Transactional(readOnly = true)
	Token getTokenByUserId(User user);
}
