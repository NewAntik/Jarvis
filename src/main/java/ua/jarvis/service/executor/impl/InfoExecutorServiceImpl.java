package ua.jarvis.service.executor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.jarvis.constant.Constants;
import ua.jarvis.service.executor.CommandExecutorService;
import ua.jarvis.service.impl.ResponderServiceImpl;

@Service
public class InfoExecutorServiceImpl implements CommandExecutorService {
	private static final Logger LOG = LoggerFactory.getLogger(InfoExecutorServiceImpl.class);

	private final ResponderServiceImpl responder;

	public InfoExecutorServiceImpl(final ResponderServiceImpl responder) {
		this.responder = responder;
	}

	@Override
	public String getType() {
		return Constants.ExecutorType.INFO;
	}

	@Override
	public void execute(final String text, final Long chatId) {
		LOG.info("InfoCommandExecuterImpl was called.");

		responder.sendMessage(chatId, Constants.UAMessages.BASE_INFO);
	}
}
