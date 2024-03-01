package com.agency.amazon.service;

import com.agency.amazon.controller.request.LoginRequest;
import com.agency.amazon.controller.request.RegistrationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

	@Transactional
	ResponseEntity<?> registration(RegistrationRequest request);

	@Transactional(readOnly = true)
	ResponseEntity<?> authorization(LoginRequest request);
}
