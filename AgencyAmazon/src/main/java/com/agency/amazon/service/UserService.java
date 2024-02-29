package com.agency.amazon.service;

import com.agency.amazon.controller.request.LoginRequest;
import com.agency.amazon.controller.request.RegistrationRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {

	ResponseEntity<?> registration(RegistrationRequest request);

	ResponseEntity<?> authorization(LoginRequest request);
}
