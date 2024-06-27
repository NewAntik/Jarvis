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
public class SurNameMidlNameDateCommandExecuterServiceImpl implements CommandExecuterService {
	private static final Logger LOG = LoggerFactory.getLogger(SurNameMidlNameDateCommandExecuterServiceImpl.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public SurNameMidlNameDateCommandExecuterServiceImpl(ResponderServiceImpl responder, UserService userService) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public String getType() {
		return Constants.ExecuterType.SUR_NAME_UNDERSCORE_MIDL_NAME_DATE;
	}

	@Override
	public void execute(final String text, final Long chatId) throws IOException {
		LOG.info("SurNameMidlNameCommandExecuterServiceImpl was called.");
		responder.sendMessage(chatId,"Триває пошук за прізвищем, по батькові та датою: " + text);
		final String[] dates = MessageChecker.getDate();
		final String[] names = text.split(" ", -1);
		final List<User> users = userService.findUserBySurNameMidlNameAndDate(
			names[0], names[2], dates[0], dates[1], dates[2]
		);

		if(users.size() > 1){
			responder.sendMessage(chatId, "За імʼям, по батькові та датою знайдено: " + users.size() + " людей.");
		}
		for (User user : users) {
			responder.createDOCXDocumentAndSend(chatId, user);
		}
	}
}
