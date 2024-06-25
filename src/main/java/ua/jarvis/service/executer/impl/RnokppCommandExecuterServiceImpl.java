package ua.jarvis.service.executer.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.jarvis.constant.Constants;
import ua.jarvis.service.UserService;
import ua.jarvis.service.executer.CommandExecuterService;
import ua.jarvis.service.impl.ResponderServiceImpl;

import java.io.IOException;

@Service
public class RnokppCommandExecuterServiceImpl implements CommandExecuterService {
	private static final Logger LOG = LoggerFactory.getLogger(RnokppCommandExecuterServiceImpl.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public RnokppCommandExecuterServiceImpl(
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
