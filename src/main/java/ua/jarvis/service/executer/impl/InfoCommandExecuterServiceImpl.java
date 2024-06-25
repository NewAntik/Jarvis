package ua.jarvis.service.executer.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ua.jarvis.constant.Constants;
import ua.jarvis.service.UserService;
import ua.jarvis.service.executer.CommandExecuterService;
import ua.jarvis.service.impl.ResponderServiceImpl;

@Component
public class InfoCommandExecuterServiceImpl implements CommandExecuterService {
	private static final Logger LOG = LoggerFactory.getLogger(InfoCommandExecuterServiceImpl.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public InfoCommandExecuterServiceImpl(final ResponderServiceImpl responder, final UserService userService) {
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

		responder.sendMessage(chatId, Constants.UAMessages.BASE_INFO);
	}
}
