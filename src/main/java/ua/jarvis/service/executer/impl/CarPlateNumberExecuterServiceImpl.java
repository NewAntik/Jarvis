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

import java.io.IOException;
import java.util.List;

@Service
public class CarPlateNumberExecuterServiceImpl implements CommandExecuterService {

	private static final Logger LOG = LoggerFactory.getLogger(CarPlateNumberExecuterServiceImpl.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public CarPlateNumberExecuterServiceImpl(
		final ResponderServiceImpl responder,
		final UserService userService
	) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public String getType() {
		return Constants.ExecuterType.CAR_PLATE_NUMBER;
	}

	@Override
	public void execute(final String plateNumber, final Long chatId) throws IOException {
		LOG.info("CarPlateNumberExecuterServiceImpl was called.");
		responder.sendMessage(chatId,"Триває пошук за автомобільним номером: " + plateNumber);
		final List<User> users = userService.findUserByCarPlateNumber(createCriteria(plateNumber));

		if(users.size() > 1){
			responder.sendMessage(chatId, "За номером авто: " + users.size() + " людей.");
		}
		for (User user : users) {
			responder.createDOCXDocumentAndSend(chatId, user);
		}
	}

	private UserCriteria createCriteria(final String plateNum) {
		return new UserCriteria.UserCriteriaBuilder().autoPlateNumber(plateNum).build();
	}
}
