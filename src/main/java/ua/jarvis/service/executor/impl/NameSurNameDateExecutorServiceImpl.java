package ua.jarvis.service.executor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.jarvis.core.constant.Constants;
import ua.jarvis.core.model.User;
import ua.jarvis.core.model.criteria.UserCriteria;
import ua.jarvis.core.model.enums.ExecutorType;
import ua.jarvis.service.UserService;
import ua.jarvis.service.executor.CommandExecutorService;
import ua.jarvis.service.impl.ResponderServiceImpl;
import ua.jarvis.service.utils.MessageChecker;

import java.io.IOException;
import java.util.List;

@Service
public class NameSurNameDateExecutorServiceImpl implements CommandExecutorService {
	private static final Logger LOG = LoggerFactory.getLogger(NameSurNameDateExecutorServiceImpl.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public NameSurNameDateExecutorServiceImpl(
		final ResponderServiceImpl responder,
		final UserService userService
	) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public String getType() {
		return ExecutorType.SUR_NAME_NAME_UNDERSCORE_DATE.getValue();
	}

	@Override
	public void execute(final String text, final Long chatId) throws IOException {
		LOG.info("NameSurNameDateCommandExecuterServiceImpl was called.");
		responder.sendMessage(chatId,"Триває пошук за прізвищем, імʼям та датою: " + text);
		final String[] dates = MessageChecker.getDate();
		final String[] names = text.split(" ", -1);
		final UserCriteria criteria = createCriteria(names[0], names[1], dates[0], dates[1], dates[2]);

		final List<User> users = userService.findUserBySurNameNameAndDate(criteria);

		if(users.size() > 1){
			responder.sendMessage(chatId, "За прізвищем, імʼям та датою знайдено: " + users.size() + " людей.");
		}
		for (User user : users) {
			responder.createDOCXDocumentAndSend(chatId, user);
		}
	}

	private UserCriteria createCriteria(
		final String surName,
		final String name,
		final String day,
		final String month,
		final String year
	) {
		return new UserCriteria.UserCriteriaBuilder()
			.surName(surName)
			.name(name)
			.day(day)
			.month(month)
			.year(year)
			.build();
	}
}
