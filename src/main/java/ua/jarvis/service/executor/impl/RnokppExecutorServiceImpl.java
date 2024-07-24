package ua.jarvis.service.executor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.jarvis.constant.Constants;
import ua.jarvis.service.UserService;
import ua.jarvis.service.executor.CommandExecutorService;
import ua.jarvis.service.impl.ResponderServiceImpl;

import java.io.IOException;

@Service
public class RnokppExecutorServiceImpl implements CommandExecutorService {
	private static final Logger LOG = LoggerFactory.getLogger(RnokppExecutorServiceImpl.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public RnokppExecutorServiceImpl(
		final ResponderServiceImpl responder,
		final UserService userService
	) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public String getType() {
		return Constants.ExecutorType.RNOKPP;
	}

	@Override
	public void execute(final String text, final Long chatId) throws IOException {
		LOG.info("RnokppCommandExecuterImpl was called.");
		responder.sendMessage(chatId,"Триває пошук за РНОКПП: " + text);
		responder.createDOCXDocumentAndSend(chatId, userService.findUserByRnokpp(text));
	}
}
