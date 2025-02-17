package ua.jarvis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.jarvis.core.model.dto.RequestDto;
import ua.jarvis.core.model.enums.ParticipantRole;
import ua.jarvis.facade.StrategyFacade;
import ua.jarvis.core.model.Participant;
import ua.jarvis.service.ParticipantService;

@Controller
public class TelegramBotController extends TelegramLongPollingBot {
	private static final Logger LOG = LoggerFactory.getLogger(TelegramBotController.class);

	private final ParticipantService participantService;

	private final String botName;

	private final StrategyFacade strategyFacade;

	public TelegramBotController(
		@Value("${bot.name}") final String botName,
		@Value("${bot.token}") final String token,
		final ParticipantService participantService,
		final StrategyFacade strategyFacade
	) {
		super(token);
		this.botName = botName;
		this.participantService = participantService;
		this.strategyFacade = strategyFacade;
	}

	@Override
	public String getBotUsername() {
		return botName;
	}

	@Override
	public void onUpdateReceived(final Update update) {
		final Long chatId = update.getMessage().getChatId();
		final Long telegramId = update.getMessage().getFrom().getId();

		final Participant participant = participantService.findByTelegramId(telegramId);

		if(update.hasMessage() && update.getMessage().hasText() && participant != null){
			try {
				LOG.info("Received user info document method was called by: {}", participant.getTelegramId());
				strategyFacade.execute(createDto(chatId, telegramId, participant.getRole(), update.getMessage().getText()));
			} catch (final Throwable e){
				LOG.error("An error occurred while processing the update", e);
				sendMessage(e.getMessage(), chatId);
			}
		} else {
			sendMessage("Something went wrong.", chatId);
		}
	}

	public void sendMessage(final String textToSend, final Long chatId){
		final SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(String.valueOf(chatId));
		sendMessage.setText(textToSend);
		try {
			execute(sendMessage);
		} catch (final TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private RequestDto createDto(final Long chatId, final Long telegramId, final ParticipantRole role, final String messageText){
		return new RequestDto(chatId, telegramId, role, messageText);
	}
}
