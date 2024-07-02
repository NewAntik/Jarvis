package ua.jarvis.service.executer.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.jarvis.constant.Constants;
import ua.jarvis.model.User;
import ua.jarvis.model.criteria.UserCriteria;
import ua.jarvis.service.UserService;
import ua.jarvis.service.executer.CommandExecuterService;
import ua.jarvis.service.impl.ResponderServiceImpl;
import ua.jarvis.service.utils.MessageChecker;

import java.io.IOException;
import java.util.List;

@Service
public class SurNameNameDateRegionExecuterServiceImpl implements CommandExecuterService {
	private static final Logger LOG = LoggerFactory.getLogger(SurNameNameDateRegionExecuterServiceImpl.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public SurNameNameDateRegionExecuterServiceImpl(
		final ResponderServiceImpl responder,
		final UserService userService
	) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public String getType() {
		return Constants.ExecuterType.SUR_NAME_NAME_UNDERSCORE_DATE_REGION;
	}

	@Override
	public void execute(final String text, final Long chatId) throws IOException {
		LOG.info("SurNameNameDataRegionExecuterServiceImpl was called.");
		responder.sendMessage(chatId,"Триває пошук за прізвищем, імʼям, датою та регіоном: " + text);
		final String[] parts = text.split(" ", -1);
		final String[] dates = MessageChecker.getDate();
		final UserCriteria criteria = createCriteria(parts[0], parts[1], dates[0], dates[1], dates[2], parts[4]);

		final List<User> users = userService.findUserBySurNameNameDateAndRegion(criteria);
		if(users.size() > 1){
			responder.sendMessage(chatId, "За ПІБ, датою та регіоном знайдено: " + users.size() + " людей.");
		}
		for (User user : users) {
			responder.createDOCXDocumentAndSend(chatId, user);
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
