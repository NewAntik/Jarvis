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
public class ThreeNamesAndRegionExecutorServiceImpl implements CommandExecutorService {
	private static final Logger LOG = LoggerFactory.getLogger(ThreeNamesAndRegionExecutorServiceImpl.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public ThreeNamesAndRegionExecutorServiceImpl(
		final ResponderServiceImpl responder,
		final UserService userService
	) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public ExecutorType getType() {
		return ExecutorType.THREE_NAMES_AND_REGION;
	}

	@Override
	public void execute(final RequestDto dto) throws IOException, InvalidFormatException {
		LOG.info("ThreeNamesAndRegionExecutorServiceImpl was called.");
		responder.sendMessage(dto.getChatId(),"Триває пошук за ПІБ та регіоном: " + dto.getMessageText());

		final String[] parts = dto.getMessageText().split(" ", -1);
		final UserCriteria criteria = createCriteria(parts[0], parts[1], parts[2], parts[3]);
		final List<User> users = userService.findUsersByCriteria(criteria);

		if(users.size() > 1){
			responder.sendMessage(dto.getChatId(), "За ПІБ та регіоном знайдено: " + users.size() + " людей.");
		}
		for (final User user : users) {
			responder.createDOCXDocumentAndSend(dto.getChatId(), user);
		}
	}

	private UserCriteria createCriteria(
		final String surName,
		final String name,
		final String midlName,
		final String region
	) {
		return new UserCriteria.UserCriteriaBuilder()
			.surName(surName)
			.name(name)
			.middleName(midlName)
			.region(region)
			.build();
	}
}
