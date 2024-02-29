package com.agency.amazon.service;

import com.agency.amazon.model.Token;
import com.agency.amazon.model.User;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {
	Token generateToken(User user);

	String getUserName(String tokenHeader);

	boolean validateToken(final String jwtToken);
}
