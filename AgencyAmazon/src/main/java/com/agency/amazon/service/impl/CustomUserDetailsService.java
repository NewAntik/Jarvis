package com.agency.amazon.service.impl;

import com.agency.amazon.model.User;
import com.agency.amazon.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByFirstName(username)
			.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

		return org.springframework.security.core.userdetails.User.builder()
			.username(user.getFirstName())
			.password(user.getPassword())
			.authorities(user.getAuthorities())
			.build();
	}
}
