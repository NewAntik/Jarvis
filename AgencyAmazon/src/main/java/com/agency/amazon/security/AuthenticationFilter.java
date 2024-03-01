package com.agency.amazon.security;

import com.agency.amazon.model.Token;
import com.agency.amazon.model.User;
import com.agency.amazon.service.TokenService;
import com.agency.amazon.service.impl.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthenticationFilter extends OncePerRequestFilter {

	private final TokenService tokenService;

	private final CustomUserDetailsService userDetailsService;

	public AuthenticationFilter(final TokenService tokenService, final CustomUserDetailsService userDetailsService) {
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

		if (tokenHeader == null) {
			filterChain.doFilter(request, response);
			return;
		}

		final String jwtToken = tokenHeader.split(" ")[1].trim();

		final String username = tokenService.getUserName(tokenHeader);

		final User user = userDetailsService.loadUserByUsername(username);

		final Token token = tokenService.findByValue(jwtToken);

		if (tokenService.validateToken(token, user)) {

			UsernamePasswordAuthenticationToken
				usernamePasswordAuthenticationToken =
				new UsernamePasswordAuthenticationToken(
					user, null, user.getAuthorities());
			usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}

		filterChain.doFilter(request, response);
	}
}
