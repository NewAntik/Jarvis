package ua.jarvis.service.executor.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.jarvis.core.model.User;
import ua.jarvis.core.model.criteria.UserCriteria;
import ua.jarvis.core.model.dto.RequestDto;
import ua.jarvis.core.model.enums.ExecutorType;
import ua.jarvis.service.UserService;
import ua.jarvis.service.executor.CommandExecutorService;
import ua.jarvis.service.impl.ResponderServiceImpl;

import java.io.IOException;
import java.util.List;

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
	public ExecutorType getType() {
		return ExecutorType.RNOKPP;
	}

	@Override
	public void execute(final RequestDto dto) throws IOException, InvalidFormatException {
		LOG.info("RnokppCommandExecuterImpl was called.");
		responder.sendMessage(dto.getChatId(),"Триває пошук за РНОКПП: " + dto.getMessageText());
		final UserCriteria criteria = createCriteria(dto.getMessageText());
		final List<User> users = userService.findUsersByCriteria(criteria);
		responder.createDOCXDocumentAndSend(dto.getChatId(), users.get(0));	}

	private UserCriteria createCriteria(final String rnokpp) {
		return new UserCriteria.UserCriteriaBuilder().rnokpp(rnokpp).build();
	}
}
