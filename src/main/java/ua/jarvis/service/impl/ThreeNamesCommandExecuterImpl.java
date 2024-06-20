package ua.jarvis.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ua.jarvis.constant.Constants;
import ua.jarvis.service.CommandExecuter;
import ua.jarvis.service.UserService;

import java.io.IOException;

@Component
public class ThreeNamesCommandExecuterImpl implements CommandExecuter {
	private static final Logger LOG = LoggerFactory.getLogger(ThreeNamesCommandExecuterImpl.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public ThreeNamesCommandExecuterImpl(
		final ResponderServiceImpl responder,
		final UserService userService
	) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public String getType() {
		return Constants.ExecuterType.NAME_SUR_NAME_MIDL_NAME;
	}

	@Override
	public void execute(final String text, final Long chatId) throws IOException {
		LOG.info("ThreeNamesCommandExecuterImpl was called.");
		responder.sendMessage(chatId,"Триває пошук за ПІБ: " + text);
		final String[] names = text.split(" ", -1);
		responder.createDOCXDocumentAndSend(chatId, userService.findUserByThreeNames(names[0], names[1], names[2]));
	}
}
