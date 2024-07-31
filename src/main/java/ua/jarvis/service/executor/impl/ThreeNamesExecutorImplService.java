package ua.jarvis.service.executor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.jarvis.core.model.User;
import ua.jarvis.core.model.criteria.UserCriteria;
import ua.jarvis.service.UserService;
import ua.jarvis.service.executor.CommandExecutorService;
import ua.jarvis.service.impl.ResponderServiceImpl;
import ua.jarvis.core.model.enums.ExecutorType;

import java.io.IOException;
import java.util.List;

@Service
public class ThreeNamesExecutorImplService implements CommandExecutorService {
	private static final Logger LOG = LoggerFactory.getLogger(ThreeNamesExecutorImplService.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public ThreeNamesExecutorImplService(
		final ResponderServiceImpl responder,
		final UserService userService
	) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public ExecutorType getType() {
		return ExecutorType.NAME_SUR_NAME_MIDL_NAME;
	}

	@Override
	public void execute(final String text, final Long chatId) throws IOException {
		LOG.info("ThreeNamesCommandExecutorImpl was called.");
		responder.sendMessage(chatId,"Триває пошук за ПІБ: " + text);
		final String[] names = text.split(" ", -1);
		final List<User> users = userService.findUsersByCriteria(createCriteria(names[0], names[1], names[2]));

		if(users.size() > 1){
			responder.sendMessage(chatId, "За ПІБ знайдено: " + users.size() + " людей.");
		}
		for (User user : users) {
			responder.createDOCXDocumentAndSend(chatId, user);
		}
	}

	private UserCriteria createCriteria(final String surName, final String name, final String middleName) {
		return new UserCriteria.UserCriteriaBuilder()
			.surName(surName)
			.name(name)
			.middleName(middleName)
		.build();
	}
}
