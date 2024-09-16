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

import java.io.IOException;
import java.util.List;

@Service
public class SurNameAndMidlNameExecutorServiceImpl implements CommandExecutorService {
	private static final Logger LOG = LoggerFactory.getLogger(SurNameAndMidlNameExecutorServiceImpl.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public SurNameAndMidlNameExecutorServiceImpl(
		final ResponderServiceImpl responder,
		final UserService userService
	) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public ExecutorType getType() {
		return ExecutorType.SURNAME_AND_MIDL_NAME;
	}

	@Override
	public void execute(final RequestDto dto) throws IOException {
		LOG.info("SurNameAndMidlNameCommandExecutorServiceImpl was called.");
		responder.sendMessage(dto.getChatId(),"Триває пошук за прізвищем та по батькові: " + dto.getMessageText());
		final String[] names = dto.getMessageText().split(" ", -1);
		final List<User> users = userService.findUsersByCriteria(createCriteria(names[0], names[2]));
		if(users.size() > 1){
			responder.sendMessage(dto.getChatId(), "За номером телефону знайдено: " + users.size() + " людей.");
		}
		for (User user : users) {
			responder.createDOCXDocumentAndSend(dto.getChatId(), user);
		}
	}

	private UserCriteria createCriteria(final String surName, final String midlName) {
		return new UserCriteria.UserCriteriaBuilder()
			.surName(surName)
			.middleName(midlName)
			.build();
	}
}
