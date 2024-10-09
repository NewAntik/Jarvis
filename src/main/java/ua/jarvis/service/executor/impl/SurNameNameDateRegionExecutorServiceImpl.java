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
import ua.jarvis.service.utils.MessageChecker;

import java.io.IOException;
import java.util.List;

@Service
public class SurNameNameDateRegionExecutorServiceImpl implements CommandExecutorService {
	private static final Logger LOG = LoggerFactory.getLogger(SurNameNameDateRegionExecutorServiceImpl.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public SurNameNameDateRegionExecutorServiceImpl(
		final ResponderServiceImpl responder,
		final UserService userService
	) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public ExecutorType getType() {
		return ExecutorType.SURNAME_NAME_UNDERSCORE_DATE_REGION;
	}

	@Override
	public void execute(final RequestDto dto) throws IOException, InvalidFormatException {
		LOG.info("SurNameNameDataRegionExecutorServiceImpl was called.");
		responder.sendMessage(dto.getChatId(),"Триває пошук за прізвищем, імʼям, датою та регіоном: " + dto.getMessageText());
		final String[] parts = dto.getMessageText().split(" ", -1);
		final String[] dates = MessageChecker.getDate();
		final UserCriteria criteria = createCriteria(parts[0], parts[1], dates[0], dates[1], dates[2], parts[4]);

		final List<User> users = userService.findUsersByCriteria(criteria);
		if(users.size() > 1){
			responder.sendMessage(dto.getChatId(), "За ПІБ, датою та регіоном знайдено: " + users.size() + " людей.");
		}
		for (final User user : users) {
			responder.createDOCXDocumentAndSend(dto.getChatId(), user);
		}
	}

	private UserCriteria createCriteria(
		final String surName,
		final String name,
		final String day,
		final String month,
		final String year,
		final String region
	) {
		return new UserCriteria.UserCriteriaBuilder()
			.surName(surName)
			.name(name)
			.day(day)
			.month(month)
			.year(year)
			.region(region)
			.build();
	}
}
