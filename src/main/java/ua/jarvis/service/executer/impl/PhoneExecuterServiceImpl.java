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
public class PhoneExecuterServiceImpl implements CommandExecuterService {
	private static final Logger LOG = LoggerFactory.getLogger(PhoneExecuterServiceImpl.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public PhoneExecuterServiceImpl(
		final ResponderServiceImpl responder,
		final UserService userService
	) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public String getType() {
		return Constants.ExecuterType.PHONE_NUMBER;
	}

	@Override
	public void execute(final String text, final Long chatId) throws IOException {
		LOG.info("PhoneCommandExecuterImpl was called.");

		final String normalizedNumber = MessageChecker.getNormalizedNumber();
		responder.sendMessage(chatId, "Триває пошук за номером телефону: " + normalizedNumber);
		final List<User> users = userService.findUsersByPhoneNumber(normalizedNumber);
		if(users.size() > 1){
			responder.sendMessage(chatId, "За номером телефону знайдено: " + users.size() + " людей.");
		}
		for (User user : users) {
			responder.createDOCXDocumentAndSend(chatId, user);
		}
	}
}
