package ua.jarvis.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ua.jarvis.constant.Constants;
import ua.jarvis.service.CommandExecuter;
import ua.jarvis.service.UserService;

import java.io.IOException;

@Component
public class PassportCommandExecuterImpl implements CommandExecuter {
	private static final Logger LOG = LoggerFactory.getLogger(PassportCommandExecuterImpl.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public PassportCommandExecuterImpl(
		final ResponderServiceImpl responder,
		final UserService userService
	) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public String getType() {
		return Constants.ExecuterType.PASSPORT;
	}

	@Override
	public void execute(final String text, final Long chatId) throws IOException {
		LOG.info("PassportCommandExecuterImpl was called.");
		responder.sendMessage(chatId,"Триває пошук за номером паспорта: " + text);
		responder.createDOCXDocumentAndSend(chatId, userService.findUserByPassportNumber(text));
	}
}
