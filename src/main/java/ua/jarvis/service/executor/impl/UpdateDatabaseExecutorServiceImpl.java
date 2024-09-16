package ua.jarvis.service.executor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.jarvis.core.model.dto.RequestDto;
import ua.jarvis.core.model.enums.ExecutorType;
import ua.jarvis.service.executor.CommandExecutorService;
import ua.jarvis.service.impl.ResponderServiceImpl;

import java.io.IOException;

@Service
public class UpdateDatabaseExecutorServiceImpl implements CommandExecutorService {
	private static final Logger LOG = LoggerFactory.getLogger(UpdateDatabaseExecutorServiceImpl.class);

	private final ResponderServiceImpl responder;

	public UpdateDatabaseExecutorServiceImpl(
		final ResponderServiceImpl responder
	) {
		this.responder = responder;
	}

	@Override
	public ExecutorType getType() {
		return ExecutorType.UPDATE_DATABASE;
	}

	@Override
	public void execute(final RequestDto dto) throws IOException {
		LOG.info("UpdateDatabaseExecutorServiceImpl was called.");
		responder.sendMessage(dto.getChatId(),"Оновлення в процессі.");
		//call DB updater service
	}
}
