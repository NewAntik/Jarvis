package ua.jarvis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ua.jarvis.constant.Constants;
import ua.jarvis.service.impl.ResponderServiceImpl;

import java.io.IOException;

@Component
public class ForeignPassportCommandExecuterImpl implements CommandExecuter {
	private static final Logger LOG = LoggerFactory.getLogger(ForeignPassportCommandExecuterImpl.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public ForeignPassportCommandExecuterImpl(
		final ResponderServiceImpl responder,
		final UserService userService
	) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public String getType() {
		return Constants.ExecuterType.FOREIGN_PASSPORT;
	}

	@Override
	public void execute(final String text, final Long chatId) throws IOException {
		LOG.info("ForeignPassportCommandExecuterImpl was called.");
		responder.sendMessage(chatId,"Триває пошук за номером закордонного паспорта: " + text);
		responder.createDOCXDocumentAndSend(chatId, userService.findUserByForeignPassportNumber(text));
	}
}
