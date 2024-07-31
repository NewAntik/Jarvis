package ua.jarvis.service.executor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.jarvis.service.UserService;
import ua.jarvis.service.executor.CommandExecutorService;
import ua.jarvis.service.impl.ResponderServiceImpl;
import ua.jarvis.core.model.enums.ExecutorType;

import java.io.IOException;

@Service
public class ThreeNamesExecutorImplService implements CommandExecutorService {
	private static final Logger LOG = LoggerFactory.getLogger(ThreeNamesExecutorImplService.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public ThreeNamesExecutorImplService(
		final ResponderServiceImpl responder,
		final UserService userService
	) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public ExecutorType getType() {
		return ExecutorType.NAME_SUR_NAME_MIDL_NAME;
	}

	@Override
	public void execute(final String text, final Long chatId) throws IOException {
		LOG.info("ThreeNamesCommandExecuterImpl was called.");
		responder.sendMessage(chatId,"Триває пошук за ПІБ: " + text);
		final String[] names = text.split(" ", -1);
		responder.createDOCXDocumentAndSend(chatId, userService.findUserByThreeNames(names[0], names[1], names[2]));
	}
}
