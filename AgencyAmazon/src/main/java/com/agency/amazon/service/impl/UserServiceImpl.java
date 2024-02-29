package com.agency.amazon.service.impl;

import com.agency.amazon.controller.request.LoginRequest;
import com.agency.amazon.controller.request.RegistrationRequest;
import com.agency.amazon.model.Token;
import com.agency.amazon.model.User;
import com.agency.amazon.model.dto.UserDto;
import com.agency.amazon.repository.UserRepository;
import com.agency.amazon.service.TokenService;
import com.agency.amazon.service.UserService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static java.lang.String.format;

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
		if (userRepository.findByLogin(request.getLogin()).isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This login is already in use.");
		}

		User user = new User(
			request.getFirstName(),
			request.getLastName(),
			request.getLogin(),
			passwordEncoder.encode(request.getPassword()),
			request.getRole(),
			getNewAsin()
		);
		user.setCreatedDate(new Date());

		userRepository.save(user);

		return ResponseEntity.ok("New user with name: " + user.getFirstName() + ", was created." );
	}

	@Override
	public ResponseEntity<?> authorization(final LoginRequest request) {
		final User user = userRepository.findByLogin(request.getLogin())
			.orElseThrow(() -> new NoSuchElementException("Invalid username or password"));

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
		}

		final Token token = tokenService.generateToken(user);

		return ResponseEntity.ok(token.getTokenValue());
	}

	private String getNewAsin(){
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
	}
}
