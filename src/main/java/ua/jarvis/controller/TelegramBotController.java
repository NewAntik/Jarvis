package ua.jarvis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.jarvis.facade.CommandExecuterFacade;
import ua.jarvis.model.Participant;
import ua.jarvis.service.ParticipantService;

@Controller
public class TelegramBotController extends TelegramLongPollingBot {
	private static final Logger LOG = LoggerFactory.getLogger(TelegramBotController.class);

	private final ParticipantService participantService;

	private final String botName;

	private Long chatId;

	private final CommandExecuterFacade facade;

	public TelegramBotController(
		@Value("${bot.name}") final String botName,
		@Value("${bot.token}") final String token,
		final ParticipantService participantService,
		final CommandExecuterFacade facade
	) {
		super(token);
		this.botName = botName;
		this.participantService = participantService;
		this.facade = facade;
	}

	@Override
	public String getBotUsername() {
		return botName;
	}

	@Override
	public void onUpdateReceived(final Update update) {
		chatId = update.getMessage().getChatId();
		final Participant participant = participantService.findByName(update.getMessage().getChat().getUserName());

		if(update.hasMessage() && update.getMessage().hasText() && participant != null ){
			final String messageText = update.getMessage().getText();
			try {
				LOG.info("Received user info document method was called by: {}", participant.getName());
				facade.execute(messageText, chatId);
			} catch (final Throwable e){
				LOG.error("An error occurred while processing the update", e);
				sendMessage(e.getMessage());
			}
		}
	}

	public void sendMessage(final String textToSend){
		final SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(String.valueOf(chatId));
		sendMessage.setText(textToSend);
		try {
			execute(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
