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
public class AddressExecutorServiceImpl implements CommandExecutorService {

	private static final Logger LOG = LoggerFactory.getLogger(AddressExecutorServiceImpl.class);

	private final ResponderServiceImpl responder;

	private final UserService userService;

	public AddressExecutorServiceImpl(
		final ResponderServiceImpl responder,
		final UserService userService
	) {
		this.responder = responder;
		this.userService = userService;
	}

	@Override
	public ExecutorType getType() {
		return ExecutorType.ADDRESS;
	}

	@Override
	public void execute(final RequestDto dto) throws IOException, InvalidFormatException {
		LOG.info("AddressExecutorServiceImpl was called.");
		responder.sendMessage(dto.getChatId(),"Триває пошук за адресою: " + dto.getMessageText());
		final String[] parts = MessageChecker.getCorrectedAddress();
		List<User> users;

		if(parts.length == 6){
			users = userService.findUsersByCriteria(createCriteria(parts[0], parts[1], parts[2]));
		} else {
			users = userService.findUsersByCriteria(createCriteriaWithFlatNumber(parts[0], parts[1], parts[2], parts[3]));
		}

		if(users.size() == 1 ){
			responder.createDOCXDocumentAndSend(dto.getChatId(), users.get(0));
		} else {
			responder.createShortDOCXDocumentAndSend(dto.getChatId(), users);
		}
	}

	private UserCriteria createCriteria(final String city, final String street, final String homeNumber) {
		return new UserCriteria.UserCriteriaBuilder()
			.city(city)
			.street(street)
			.homeNumber(homeNumber)
			.build();
	}

	private UserCriteria createCriteriaWithFlatNumber(
		final String city,
		final String street,
		final String homeNumber,
		final String flatNumber
	) {
		return new UserCriteria.UserCriteriaBuilder()
			.city(city)
			.street(street)
			.homeNumber(homeNumber)
			.flatNumber(flatNumber)
			.build();
	}
}
