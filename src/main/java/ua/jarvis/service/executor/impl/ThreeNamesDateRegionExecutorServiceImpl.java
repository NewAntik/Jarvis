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
public class ThreeNamesDateRegionExecutorServiceImpl implements CommandExecutorService {
	private static final Logger LOG = LoggerFactory.getLogger(ThreeNamesDateRegionExecutorServiceImpl.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public ThreeNamesDateRegionExecutorServiceImpl(
		final ResponderServiceImpl responder,
		final UserService userService
	) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public ExecutorType getType() {
		return ExecutorType.THREE_NAMES_DATE_REGION;
	}

	@Override
	public void execute(final RequestDto dto ) throws IOException, InvalidFormatException {
		LOG.info("ThreeNamesDateRegionCommandExecutorServiceImpl was called.");
		responder.sendMessage(dto.getChatId(),"Триває пошук за ПІБ, датою та регіоном: " + dto.getMessageText());
		final String[] parts = dto.getMessageText().split(" ", -1);
		final String[] dates = MessageChecker.getDate();
		final UserCriteria criteria = createCriteria(parts[0], parts[1], parts[2], dates[0], dates[1], dates[2], parts[4]);

		final List<User> users = userService.findUsersByCriteria(criteria);
		if(users.size() > 1){
			responder.sendMessage(dto.getChatId(), "За ПІБ, датою та регіоном знайдено: " + users.size() + " людей.");
		}
		if(users.size() == 1 ){
			responder.createDOCXDocumentAndSend(dto.getChatId(), users.get(0));
		} else {
			responder.createShortDOCXDocumentAndSend(dto.getChatId(), users);
		}
	}

	private UserCriteria createCriteria(
		final String surName,
		final String name,
		final String midlName,
		final String day,
		final String month,
		final String year,
		final String region
	) {
		return new UserCriteria.UserCriteriaBuilder()
			.surName(surName)
			.name(name)
			.middleName(midlName)
			.day(day)
			.month(month)
			.year(year)
			.region(region)
			.build();
	}
}
