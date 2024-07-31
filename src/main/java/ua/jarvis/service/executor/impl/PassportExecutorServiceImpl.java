package ua.jarvis.service.executor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ua.jarvis.core.model.User;
import ua.jarvis.core.model.criteria.UserCriteria;
import ua.jarvis.core.model.enums.ExecutorType;
import ua.jarvis.service.UserService;
import ua.jarvis.service.executor.CommandExecutorService;
import ua.jarvis.service.impl.ResponderServiceImpl;

import java.io.IOException;
import java.util.List;

@Component
public class PassportExecutorServiceImpl implements CommandExecutorService {
	private static final Logger LOG = LoggerFactory.getLogger(PassportExecutorServiceImpl.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public PassportExecutorServiceImpl(
		final ResponderServiceImpl responder,
		final UserService userService
	) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public ExecutorType getType() {
		return ExecutorType.PASSPORT;
	}

	@Override
	public void execute(final String text, final Long chatId) throws IOException {
		LOG.info("PassportCommandExecutorImpl was called.");
		responder.sendMessage(chatId,"Триває пошук за номером паспорта: " + text);
		final UserCriteria criteria = createCriteria(text);
		final List<User> users = userService.findUsersByCriteria(criteria);

		responder.createDOCXDocumentAndSend(chatId, users.get(0));
	}

	private UserCriteria createCriteria(final String foreignPassport) {
		return new UserCriteria.UserCriteriaBuilder().passport(foreignPassport).build();
	}
}
