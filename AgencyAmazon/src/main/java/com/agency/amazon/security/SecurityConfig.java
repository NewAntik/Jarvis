package com.agency.amazon.security;

import com.agency.amazon.service.TokenService;
import com.agency.amazon.service.impl.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final CustomUserDetailsService userDetailsService;

	private final TokenService tokenService;

	public SecurityConfig(CustomUserDetailsService userDetailsService, TokenService tokenService) {
		this.userDetailsService = userDetailsService;
		this.tokenService = tokenService;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf(crsf -> crsf.disable())
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(HttpMethod.GET, "/v1.0/user/registration").permitAll()
				.requestMatchers(HttpMethod.POST, "/v1.0/user/login").permitAll()
				.anyRequest().authenticated()
			)
			.addFilterBefore(
				new AuthenticationFilter(tokenService, userDetailsService),
				UsernamePasswordAuthenticationFilter.class
			)
			.build();
	}
}
