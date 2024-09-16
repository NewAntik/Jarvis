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
public class SurNameAndNameAndRegionExecutorServiceImpl implements CommandExecutorService {
	private static final Logger LOG = LoggerFactory.getLogger(SurNameAndNameAndRegionExecutorServiceImpl.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public SurNameAndNameAndRegionExecutorServiceImpl(
		final ResponderServiceImpl responder,
		final UserService userService
	) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public ExecutorType getType() {
		return ExecutorType.SURNAME_NAME_AND_REGION;
	}

	@Override
	public void execute(final RequestDto dto) throws IOException {
		LOG.info("ThreeNamesAndRegionExecutorServiceImpl was called.");
		responder.sendMessage(dto.getChatId(),"Триває пошук за прізвищем, імʼям та регіоном: " + dto.getMessageText());

		final String[] parts = dto.getMessageText().split(" ", -1);
		final UserCriteria criteria = createCriteria(parts[0], parts[1], parts[3]);
		final List<User> users = userService.findUsersByCriteria(criteria);

		if(users.size() > 1){
			responder.sendMessage(dto.getChatId(), "За ПІБ та регіоном знайдено: " + users.size() + " людей.");
		}
		for (User user : users) {
			responder.createDOCXDocumentAndSend(dto.getChatId(), user);
		}
	}

	private UserCriteria createCriteria(
		final String surName,
		final String name,
		final String region
	) {
		return new UserCriteria.UserCriteriaBuilder()
			.surName(surName)
			.name(name)
			.region(region)
			.build();
	}
}
