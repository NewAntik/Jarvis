package ua.jarvis.service.executor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.jarvis.core.constant.Constants;
import ua.jarvis.core.model.criteria.UserCriteria;
import ua.jarvis.core.model.enums.ExecutorType;
import ua.jarvis.service.UserService;
import ua.jarvis.service.executor.CommandExecutorService;
import ua.jarvis.service.impl.ResponderServiceImpl;

import java.io.IOException;

@Service
public class SurNameAndMidlNameExecutorServiceImpl implements CommandExecutorService {
	private static final Logger LOG = LoggerFactory.getLogger(SurNameAndMidlNameExecutorServiceImpl.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public SurNameAndMidlNameExecutorServiceImpl(
		final ResponderServiceImpl responder,
		final UserService userService
	) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public String getType() {
		return ExecutorType.SUR_NAME_AND_MIDL_NAME.getValue();
	}

	@Override
	public void execute(final String text, final Long chatId) throws IOException {
		LOG.info("SurNameAndMidlNameCommandExecuterServiceImpl was called.");
		responder.sendMessage(chatId,"Триває пошук за прізвищем та по батькові: " + text);
		final String[] names = text.split(" ", -1);
		responder.createDOCXDocumentAndSend(chatId, userService.findUserBySurNameAndMidlName(createCriteria(names[0], names[2])));
	}

	private UserCriteria createCriteria(final String surName, final String midlName) {
		return new UserCriteria.UserCriteriaBuilder()
			.surName(surName)
			.middleName(midlName)
			.build();
	}
}
