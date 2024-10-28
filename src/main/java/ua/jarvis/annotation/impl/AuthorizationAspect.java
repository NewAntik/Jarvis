package ua.jarvis.annotation.impl;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ua.jarvis.annotation.Authorize;
import ua.jarvis.core.model.dto.RequestDto;
import ua.jarvis.core.model.enums.ParticipantRole;

@Aspect
@Component
public class AuthorizationAspect {
	private static final Logger LOG = LoggerFactory.getLogger(AuthorizationAspect.class);

	@Before("@annotation(authorize)")
	public void checkAuthorization(final JoinPoint joinPoint, final Authorize authorize){
		final ParticipantRole requiredRole = authorize.value();

		final RequestDto dto = (RequestDto) joinPoint.getArgs()[0];
		LOG.info("checkAuthorization was called with role:{}", dto.getRole());

		if (dto.getRole() != requiredRole) {

			throw new SecurityException("User does not have the required role: " + requiredRole);
		}
	}
}
