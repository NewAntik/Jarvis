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
public class ThreeNamesExecuterImplService implements CommandExecuterService {
	private static final Logger LOG = LoggerFactory.getLogger(ThreeNamesExecuterImplService.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public ThreeNamesExecuterImplService(
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
