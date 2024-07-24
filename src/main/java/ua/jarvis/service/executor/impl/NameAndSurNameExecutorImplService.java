package ua.jarvis.service.executor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.jarvis.core.model.enums.ExecutorType;
import ua.jarvis.service.UserService;
import ua.jarvis.service.executor.CommandExecutorService;
import ua.jarvis.service.impl.ResponderServiceImpl;

import java.io.IOException;

@Service
public class NameAndSurNameExecutorImplService implements CommandExecutorService {
	private static final Logger LOG = LoggerFactory.getLogger(NameAndSurNameExecutorImplService.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public NameAndSurNameExecutorImplService(
		final ResponderServiceImpl responder,
		final UserService userService
	) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public String getType() {
		return ExecutorType.SUR_NAME_AND_NAME.getValue();
	}

	@Override
	public void execute(final String text, final Long chatId) throws IOException {
		LOG.info("NameAndSurNameCommandExecuterImpl was called.");
		responder.sendMessage(chatId,"Триває пошук за прізвищем та імʼям: " + text);
		final String[] names = text.split(" ", -1);
		responder.createDOCXDocumentAndSend(chatId, userService.findUserBySurNameAndName(names[0], names[1]));
	}
}
