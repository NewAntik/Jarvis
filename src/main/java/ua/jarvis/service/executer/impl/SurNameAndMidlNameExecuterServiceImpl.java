package ua.jarvis.service.executer.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.jarvis.constant.Constants;
import ua.jarvis.model.criteria.UserCriteria;
import ua.jarvis.service.UserService;
import ua.jarvis.service.executer.CommandExecuterService;
import ua.jarvis.service.impl.ResponderServiceImpl;

import java.io.IOException;

@Service
public class SurNameAndMidlNameExecuterServiceImpl implements CommandExecuterService {
	private static final Logger LOG = LoggerFactory.getLogger(SurNameAndMidlNameExecuterServiceImpl.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public SurNameAndMidlNameExecuterServiceImpl(
		final ResponderServiceImpl responder,
		final UserService userService
	) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public String getType() {
		return Constants.ExecuterType.SUR_NAME_MIDL_NAME;
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
