package ua.jarvis.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ua.jarvis.constant.Constants;
import ua.jarvis.service.CommandExecuter;
import ua.jarvis.service.UserService;

@Component
public class InfoCommandExecuterImpl implements CommandExecuter {
	private static final Logger LOG = LoggerFactory.getLogger(InfoCommandExecuterImpl.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public InfoCommandExecuterImpl(final ResponderServiceImpl responder, final UserService userService) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public String getType() {
		return Constants.ExecuterType.INFO;
	}

	@Override
	public void execute(final String text, final Long chatId) {
		LOG.info("InfoCommandExecuterImpl was called.");

		responder.sendMessage(chatId, userService.getInfo());
	}
}
