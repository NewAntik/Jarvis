package com.agency.amazon.controller;

import com.agency.amazon.controller.request.LoginRequest;
import com.agency.amazon.controller.request.RegistrationRequest;
import com.agency.amazon.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.agency.amazon.controller.UserController.BASE_URL;

@RestController
@RequestMapping(BASE_URL)
public class UserController {
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	public static final String BASE_URL = "/v1.0";

	private final UserService userService;

	UserController(final UserService userService){
		this.userService = userService;
	}

	@GetMapping("/user/registration")
	public ResponseEntity<?> registration(@RequestBody RegistrationRequest request){
		LOG.debug("Registration method was called in UserController.");

		return userService.registration(request);
	}

	@PostMapping("/user/login")
	public ResponseEntity<?> authenticateUser(final @RequestBody LoginRequest request){
		LOG.debug("AuthenticateUser method was called in UserController.");

		return userService.authorization(request);
	}
}
