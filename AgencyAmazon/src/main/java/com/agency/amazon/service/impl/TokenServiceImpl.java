package com.agency.amazon.service.impl;

import com.agency.amazon.model.Token;
import com.agency.amazon.model.User;
import com.agency.amazon.repository.TokenRepository;
import com.agency.amazon.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl implements TokenService {

	private final String defaultTimeZone;

	private final int tokenLifeHours;

	private final String secret;

	private final TokenRepository tokenRepository;

	public TokenServiceImpl(
		@Value("${token.life-in-hours}") final int tokenLifeHours,
		@Value("${default.time-zone.name}") final String defaultTimeZone,
		@Value("${token.secret}") final String secret,
		final TokenRepository tokenRepository
	) {
		this.defaultTimeZone = defaultTimeZone;
		this.tokenLifeHours = tokenLifeHours;
		this.secret = secret;
		this.tokenRepository = tokenRepository;
	}

	@Override
	public Token generateToken(final User user) {
		final String tokenValue = generateToken(user.getFirstName());
		final LocalDateTime tokenExpirationDate = calculateTokenExpirationDate();

		return tokenRepository.save(
			new Token(tokenValue, user.getId(), tokenExpirationDate, LocalDateTime.now(), LocalDateTime.now())
		);
	}

	@Override
	public String getUserName(String tokenHeader) {
		final String token = tokenHeader.substring(7);

		return getClaimFromToken(token).getSubject();
	}

	@Override
	public boolean validateToken(final Token token, final User user) {
		final Token userToken = tokenRepository.findByUserId(user.getId())
			.orElseThrow(() -> new NoSuchElementException("There is no such token."));

		if (isTokenExpired(userToken.getValue())) {
			refreshToken(token);
		}

		return true;
	}

	private void refreshToken(final Token token) {
		token.setExpirationDate(calculateTokenExpirationDate());
		token.setUpdatedDate(LocalDateTime.now());
		tokenRepository.save(token);
	}

	@Override
	public Token findByValue(final String value) {
		return tokenRepository.findByValue(value)
			.orElseThrow(() -> new NoSuchElementException("Invalid token value"));
	}

	@Override
	public Token getTokenByUserId(final User user) {
		return tokenRepository.findByUserId(user.getId())
			.orElseThrow(() -> new NoSuchElementException("Invalid token value there is no token with such user id."));
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
