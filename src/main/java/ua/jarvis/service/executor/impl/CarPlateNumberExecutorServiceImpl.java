package ua.jarvis.service.executor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.jarvis.core.model.User;
import ua.jarvis.core.model.criteria.UserCriteria;
import ua.jarvis.core.model.enums.ExecutorType;
import ua.jarvis.service.UserService;
import ua.jarvis.service.executor.CommandExecutorService;
import ua.jarvis.service.impl.ResponderServiceImpl;

import java.io.IOException;
import java.util.List;

@Service
public class CarPlateNumberExecutorServiceImpl implements CommandExecutorService {

	private static final Logger LOG = LoggerFactory.getLogger(CarPlateNumberExecutorServiceImpl.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public CarPlateNumberExecutorServiceImpl(
		final ResponderServiceImpl responder,
		final UserService userService
	) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public ExecutorType getType() {
		return ExecutorType.CAR_PLATE_NUMBER;
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
