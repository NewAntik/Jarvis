package com.agency.amazon.configuration;

import com.agency.amazon.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthenticationFilter extends OncePerRequestFilter {

	private final TokenService tokenService;

	private final UserDetailsService userDetailsService;


	public AuthenticationFilter(final TokenService tokenService, final UserDetailsService userDetailsService) {
		this.tokenService = tokenService;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(
		final HttpServletRequest request,
		final HttpServletResponse response,
		final FilterChain filterChain
	) throws ServletException, IOException {
		final String tokenHeader = request.getHeader("Authorization");

		if(tokenHeader == null || !tokenHeader.startsWith("Bearer ")){
			filterChain.doFilter(request, response);
		}

		String jwtToken = tokenHeader.split(" ")[1].trim();
		final String username = tokenService.getUserName(tokenHeader);

		final UserDetails userValueObject = userDetailsService.loadUserByUsername(username);

		if (tokenService.validateToken(jwtToken, userValueObject.getUsername())) {

			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userValueObject, null, userValueObject.getAuthorities());
			usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}

		filterChain.doFilter(request, response);
	}
}
