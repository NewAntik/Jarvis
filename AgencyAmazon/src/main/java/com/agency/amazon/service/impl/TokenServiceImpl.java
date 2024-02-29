package com.agency.amazon.service.impl;

import com.agency.amazon.model.Token;
import com.agency.amazon.model.User;
import com.agency.amazon.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl implements TokenService {

	private final String defaultTimeZone;

	private final int tokenLifeHours;

	private final byte[] secret;

	public TokenServiceImpl(
		@Value("${token.life-in-hours}") final int tokenLifeHours,
		@Value("${default.time-zone.name}") final String defaultTimeZone,
		@Value("${token.secret}") final String secret
	) {
		this.defaultTimeZone = defaultTimeZone;
		this.tokenLifeHours = tokenLifeHours;
		this.secret = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
	}

	@Override
	public Token generateToken(final User user) {
		final String tokenValue = generateToken(user.getFirstName());
		final LocalDateTime tokenExpirationDate = calculateTokenExpirationDate();

		return new Token(
			tokenValue,
			user,
			tokenExpirationDate,
			LocalDateTime.now(),
			LocalDateTime.now()
		);
	}

	@Override
	public String getUserName(String tokenHeader) {
		final String token = tokenHeader.substring(7);

		return getClaimFromToken(token).getSubject();
	}

	@Override
	public boolean validateToken(final String jwtToken) {
		return !isTokenExpired(jwtToken);
	}

	private LocalDateTime calculateTokenExpirationDate() {
		final ZonedDateTime now = LocalDateTime.now().atZone(ZoneId.of(defaultTimeZone));
		return now.plusHours(tokenLifeHours).toLocalDateTime();
	}

	private String generateToken(String username) {
		return Jwts.builder()
			.setSubject(username)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(tokenLifeHours)))
			.signWith(SignatureAlgorithm.HS256, secret)
			.compact();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getClaimFromToken(token).getExpiration();
		return expiration.before(new Date());
	}


	private Claims getClaimFromToken(String token) {
		return Jwts.parser()
			.setSigningKey(secret)
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}
}
