package ua.jarvis.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.jarvis.constant.Constants;
import ua.jarvis.service.CommandExecuter;
import ua.jarvis.service.UserService;

import java.io.IOException;

@Service
public class RnokppCommandExecuterImpl implements CommandExecuter {
	private static final Logger LOG = LoggerFactory.getLogger(RnokppCommandExecuterImpl.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public RnokppCommandExecuterImpl(
		final ResponderServiceImpl responder,
		final UserService userService
	) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public String getType() {
		return Constants.ExecuterType.RNOKPP;
	}

	@Override
	public void execute(final String text, final Long chatId) throws IOException {
		LOG.info("RnokppCommandExecuterImpl was called.");
		responder.sendMessage(chatId,"Триває пошук за РНОКПП: " + text);
		responder.createDOCXDocumentAndSend(chatId, userService.findUserByRnokpp(text));
	}
}
