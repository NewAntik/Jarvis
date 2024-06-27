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
public class ThreeNamesDateRegionCommandExecuterServiceImpl implements CommandExecuterService {
	private static final Logger LOG = LoggerFactory.getLogger(ThreeNamesDateRegionCommandExecuterServiceImpl.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public ThreeNamesDateRegionCommandExecuterServiceImpl(
		final ResponderServiceImpl responder,
		final UserService userService
	) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public String getType() {
		return Constants.ExecuterType.THREE_NAMES_DATE_REGION;
	}

	@Override
	public void execute(final String text, final Long chatId) throws IOException {
		LOG.info("ThreeNamesDateRegionCommandExecuterServiceImpl was called.");
		responder.sendMessage(chatId,"Триває пошук за ПІБ, датою та регіоном: " + text);
		final String[] parts = text.split(" ", -1);
		final String[] dates = MessageChecker.getDate();
		final List<User> users = userService.findUserByThreeNamesDateAndRegion(
			parts[0], parts[1], parts[2], parts[4],dates[0], dates[1], dates[2]
		);
		if(users.size() > 1){
			responder.sendMessage(chatId, "За ПІБ, датою та регіоном знайдено: " + users.size() + " людей.");
		}
		for (User user : users) {
			responder.createDOCXDocumentAndSend(chatId, user);
		}
	}
}
