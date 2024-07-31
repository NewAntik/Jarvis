package ua.jarvis.service.executor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.jarvis.core.model.User;
import ua.jarvis.core.model.criteria.UserCriteria;
import ua.jarvis.core.model.enums.ExecutorType;
import ua.jarvis.service.UserService;
import ua.jarvis.service.executor.CommandExecutorService;
import ua.jarvis.service.impl.ResponderServiceImpl;

import java.io.IOException;
import java.util.List;

@Service
public class ForeignPassportExecutorImplService implements CommandExecutorService {
	private static final Logger LOG = LoggerFactory.getLogger(ForeignPassportExecutorImplService.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public ForeignPassportExecutorImplService(
		final ResponderServiceImpl responder,
		final UserService userService
	) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public ExecutorType getType() {
		return ExecutorType.FOREIGN_PASSPORT;
	}

	@Override
	public void execute(final String text, final Long chatId) throws IOException {
		LOG.info("ForeignPassportCommandExecutorImpl was called.");
		responder.sendMessage(chatId,"Триває пошук за номером закордонного паспорта: " + text);
		final UserCriteria criteria = createCriteria(text);
		final List<User> users = userService.findUsersByCriteria(criteria);
		responder.createDOCXDocumentAndSend(chatId, users.get(0));

	}
	private UserCriteria createCriteria(final String foreignPassport) {
		return new UserCriteria.UserCriteriaBuilder().foreignPassport(foreignPassport).build();
	}
}
