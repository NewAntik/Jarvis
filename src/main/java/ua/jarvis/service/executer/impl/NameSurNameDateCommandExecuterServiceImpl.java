package ua.jarvis.service.executer.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.jarvis.constant.Constants;
import ua.jarvis.model.User;
import ua.jarvis.service.UserService;
import ua.jarvis.service.executer.CommandExecuterService;
import ua.jarvis.service.impl.ResponderServiceImpl;
import ua.jarvis.service.utils.MessageChecker;

import java.io.IOException;
import java.util.List;

@Service
public class NameSurNameDateCommandExecuterServiceImpl implements CommandExecuterService {
	private static final Logger LOG = LoggerFactory.getLogger(NameSurNameDateCommandExecuterServiceImpl.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public NameSurNameDateCommandExecuterServiceImpl(
		final ResponderServiceImpl responder,
		final UserService userService
	) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public String getType() {
		return Constants.ExecuterType.NAME_SUR_NAME_UNDERSCORE_DATE;
	}

	@Override
	public void execute(final String text, final Long chatId) throws IOException {
		LOG.info("NameSurNameDateCommandExecuterServiceImpl was called.");
		responder.sendMessage(chatId,"Триває пошук за прізвищем, імʼям та датою: " + text);
		final String[] dates = MessageChecker.getDate();
		final String[] names = text.split(" ", -1);
		final List<User> users = userService.findUserBySurNameNameAndDate(
			names[0], names[1], dates[0], dates[1], dates[2]
		);

		if(users.size() > 1){
			responder.sendMessage(chatId, "За прізвищем, імʼям та датою знайдено: " + users.size() + " людей.");
		}
		for (User user : users) {
			responder.createDOCXDocumentAndSend(chatId, user);
		}
	}
}
