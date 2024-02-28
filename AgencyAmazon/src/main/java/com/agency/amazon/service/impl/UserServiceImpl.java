package com.agency.amazon.service.impl;

import com.agency.amazon.controller.request.LoginRequest;
import com.agency.amazon.controller.request.RegistrationRequest;
import com.agency.amazon.model.Token;
import com.agency.amazon.model.User;
import com.agency.amazon.repository.UserRepository;
import com.agency.amazon.service.TokenService;
import com.agency.amazon.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

	private final TokenService tokenService;

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(
		final TokenService tokenService,
		final UserRepository userRepository,
		final PasswordEncoder passwordEncoder
	) {
		this.tokenService = tokenService;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public ResponseEntity<?> registration(final RegistrationRequest request) {
		if (userRepository.findByLogin(request.getLogin()) != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This login is already in use.");
		}

		User user = new User(
			request.getFirstName(),
			request.getLastName(),
			request.getLogin(),
			passwordEncoder.encode(request.getPassword())
		);

		userRepository.save(user);

		return ResponseEntity.ok("New user with user name: " + user.getFirstName() + ", has been successfully registered.");
	}

	@Override
	public ResponseEntity<?> authorization(final LoginRequest request) {
		User user = userRepository.findByLogin(request.getLogin());

		if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
		}

		final Token token = tokenService.generateToken(user);
		Map<String, Token> response = new HashMap<>();
		response.put("token", token);

		return ResponseEntity.ok(response);
	}

}
