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
public class ForeignPassportExecutorImplService implements CommandExecutorService {
	private static final Logger LOG = LoggerFactory.getLogger(ForeignPassportExecutorImplService.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public ForeignPassportExecutorImplService(
		final ResponderServiceImpl responder,
		final UserService userService
	) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public String getType() {
		return ExecutorType.FOREIGN_PASSPORT.getValue();
	}

	@Override
	public void execute(final String text, final Long chatId) throws IOException {
		LOG.info("ForeignPassportCommandExecuterImpl was called.");
		responder.sendMessage(chatId,"Триває пошук за номером закордонного паспорта: " + text);
		responder.createDOCXDocumentAndSend(chatId, userService.findUserByForeignPassportNumber(text));
	}
}
