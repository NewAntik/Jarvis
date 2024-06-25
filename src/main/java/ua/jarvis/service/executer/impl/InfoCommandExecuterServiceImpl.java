package ua.jarvis.service.executer.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.jarvis.constant.Constants;
import ua.jarvis.service.executer.CommandExecuterService;
import ua.jarvis.service.impl.ResponderServiceImpl;

@Service
public class InfoCommandExecuterServiceImpl implements CommandExecuterService {
	private static final Logger LOG = LoggerFactory.getLogger(InfoCommandExecuterServiceImpl.class);

	private final ResponderServiceImpl responder;

	public InfoCommandExecuterServiceImpl(final ResponderServiceImpl responder) {
		this.responder = responder;
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
