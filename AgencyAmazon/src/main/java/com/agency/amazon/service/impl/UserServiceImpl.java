package com.agency.amazon.service.impl;

import com.agency.amazon.controller.request.LoginRequest;
import com.agency.amazon.controller.request.RegistrationRequest;
import com.agency.amazon.model.Token;
import com.agency.amazon.model.User;
import com.agency.amazon.model.dto.UserDto;
import com.agency.amazon.repository.UserRepository;
import com.agency.amazon.service.TokenService;
import com.agency.amazon.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
		User user = userRepository.findByLogin(request.getLogin());

		if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
		}

		final Token token = tokenService.generateToken(user);

		return ResponseEntity.ok(token.getTokenValue());
	}

	@Override
	public List<UserDto> getStatisticByDate(final Date date) {

		return mapToUserDto(userRepository.findByCreatedDate(date));
	}

	@Override
	public List<UserDto> getStatisticBetweenDates(final Date fromDate, final Date toDate) {
		return mapToUserDto(userRepository.findBetweenDates(fromDate, toDate));
	}

	@Override
	public List<UserDto> getStatisticWithTime() {
		return mapToUserDto(userRepository.findAllByCreatedDateIsNotNull());
	}

	@Override
	public UserDto getStatisticByAsin(final String asin) {
		final User user = userRepository.findByAsin(asin);

		return new UserDto
			(
				user.getFirstName(),
				user.getLastName(),
				user.getRole(),
				user.getCreatedDate(),
				user.getAsin()
			);
	}

	@Override
	public List<UserDto> getStatisticByAsinsList(final List<String> asins) {
		return mapToUserDto(userRepository.findAllByAsin(asins));
	}

	@Override
	public List<UserDto> getStatisticWithAsins() {
		return mapToUserDto(userRepository.findAllByAsinIsNotNull());
	}

	private List<UserDto> mapToUserDto(final List<User> users) {
		return users.stream()
					.map(user -> new UserDto
						(
							user.getFirstName(),
							user.getLastName(),
							user.getRole(),
							user.getCreatedDate(),
							user.getAsin())
						)
					.toList();
	}

	private String getNewAsin(){
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
	}
}
