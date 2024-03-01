package com.agency.amazon.service.impl;

import com.agency.amazon.model.User;
import com.agency.amazon.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService{

	private final UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByFirstName(username)
			.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
	}
}
