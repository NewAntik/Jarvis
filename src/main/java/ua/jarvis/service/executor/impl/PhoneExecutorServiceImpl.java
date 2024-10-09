package ua.jarvis.service.executor.impl;

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
import ua.jarvis.service.utils.MessageChecker;

import java.io.IOException;
import java.util.List;

@Service
public class PhoneExecutorServiceImpl implements CommandExecutorService {
	private static final Logger LOG = LoggerFactory.getLogger(PhoneExecutorServiceImpl.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public PhoneExecutorServiceImpl(
		final ResponderServiceImpl responder,
		final UserService userService
	) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public ExecutorType getType() {
		return ExecutorType.PHONE_NUMBER;
	}

	@Override
	public void execute(final RequestDto dto) throws IOException {
		LOG.info("PhoneCommandExecutorImpl was called.");
		final String normalizedNumber = MessageChecker.getNormalizedNumber();
		responder.sendMessage(dto.getChatId(), "Триває пошук за номером телефону: " + normalizedNumber);
		final List<User> users = userService.findUsersByCriteria(createCriteria(dto.getMessageText()));
		if(users.size() > 1){
			responder.sendMessage(dto.getChatId(), "За номером телефону знайдено: " + users.size() + " людей.");
		}
		for (final User user : users) {
			responder.createDOCXDocumentAndSend(dto.getChatId(), user);
		}
	}

	private UserCriteria createCriteria(final String phoneNum) {
		return new UserCriteria.UserCriteriaBuilder().phoneNumber(phoneNum).build();
	}
}
