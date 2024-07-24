package ua.jarvis.service.executor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.jarvis.constant.Constants;
import ua.jarvis.model.User;
import ua.jarvis.model.criteria.UserCriteria;
import ua.jarvis.service.UserService;
import ua.jarvis.service.executor.CommandExecutorService;
import ua.jarvis.service.impl.ResponderServiceImpl;
import ua.jarvis.service.utils.MessageChecker;

import java.io.IOException;
import java.util.List;

@Service
public class NameMidlNameDateExecutorServiceImpl implements CommandExecutorService {
	private static final Logger LOG = LoggerFactory.getLogger(NameMidlNameDateExecutorServiceImpl.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public NameMidlNameDateExecutorServiceImpl(ResponderServiceImpl responder, UserService userService) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public String getType() {
		return Constants.ExecutorType.UNDERSCORE_NAME_MIDL_NAME_DATE;
	}

	@Override
	public void execute(final String text, final Long chatId) throws IOException {
		LOG.info("NameMidlNameDateCommandExecuterServiceImpl was called.");
		responder.sendMessage(chatId,"Триває пошук за імʼям, по батькові та датою: " + text);
		final String[] dates = MessageChecker.getDate();
		final String[] names = text.split(" ", -1);
		final UserCriteria criteria = createCriteria(names[1], names[2], dates[0], dates[1], dates[2]);

		final List<User> users = userService.findUserByNameMidlNameAndDate(criteria);
		if(users.size() > 1){
			responder.sendMessage(chatId, "За імʼям, по батькові та датою знайдено: " + users.size() + " людей.");
		}
		for (User user : users) {
			responder.createDOCXDocumentAndSend(chatId, user);
		}
	}

	private UserCriteria createCriteria(
		final String name,
		final String midlName,
		final String day,
		final String month,
		final String year
	) {
		return new UserCriteria.UserCriteriaBuilder()
			.name(name)
			.middleName(midlName)
			.day(day)
			.month(month)
			.year(year)
			.build();
	}
}
