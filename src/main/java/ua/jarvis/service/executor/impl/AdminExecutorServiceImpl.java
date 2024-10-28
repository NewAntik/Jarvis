package ua.jarvis.service.executor.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ua.jarvis.annotation.Authorize;
import ua.jarvis.core.constant.Constants;
import ua.jarvis.core.model.Participant;
import ua.jarvis.core.model.dto.RequestDto;
import ua.jarvis.core.model.enums.ExecutorType;
import ua.jarvis.core.model.enums.ParticipantRole;
import ua.jarvis.service.ParticipantService;
import ua.jarvis.service.executor.CommandExecutorService;
import ua.jarvis.service.impl.ResponderServiceImpl;

import java.io.IOException;

@Component
public class AdminExecutorServiceImpl implements CommandExecutorService {
	private static final Logger LOG = LoggerFactory.getLogger(AdminExecutorServiceImpl.class);

	private final ResponderServiceImpl responder;

	private final ParticipantService participantService;

	public AdminExecutorServiceImpl(
		final ResponderServiceImpl responder,
		final ParticipantService participantService
	) {
		this.responder = responder;
		this.participantService = participantService;
	}

	@Override
	public ExecutorType getType() {
		return ExecutorType.ADMIN;
	}

	@Authorize(ParticipantRole.ADMIN)
	@Override
	public void execute(final RequestDto dto) throws IOException, InvalidFormatException {
		LOG.info("AdminExecutorServiceImpl was called.");
		final Long telegramId = Long.valueOf(dto.getMessageText().split(" ", -1)[1]);

		if(dto.getMessageText().contains(Constants.AdminCommands.ADD)){
			responder.sendMessage(dto.getChatId(),"Запит на додавання прийнятий: " + telegramId);
			add(telegramId);
			responder.sendMessage(dto.getChatId(),"Юзер успішно додан.");

		} else if(!isSelfId(dto.getTelegramId(), telegramId) && dto.getMessageText().contains(Constants.AdminCommands.DELETE)){
			responder.sendMessage(dto.getChatId(),"Запит видалення прийнятий: " + telegramId);
			delete(telegramId);
			responder.sendMessage(dto.getChatId(),"Юзер успішно видалений.");
		}
	}

	private void add(final Long telegramId){
		participantService.saveNew(new Participant(telegramId, ParticipantRole.STAFF));
	}

	private void delete(final Long telegramId){
		participantService.deleteByTelegramId(telegramId);
	}

	private boolean isSelfId(final Long selfTelegramId, final Long telegramId){
		return selfTelegramId.equals(telegramId);
	}
}
