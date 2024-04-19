package ua.jarvis.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.jarvis.constant.Constants;
import ua.jarvis.model.Participant;
import ua.jarvis.service.ParticipantService;
import ua.jarvis.service.UserService;

import java.util.Optional;

@Service
public class TelegramBotService extends TelegramLongPollingBot {

	private final UserService userService;

	private final ParticipantService participantService;

	private final String botName;

	public TelegramBotService(
		@Value("${bot.name}") final String botName,
		@Value("${bot.token}") final String token,
		final UserService userService, ParticipantService participantService
	) {
		super(token);
		this.botName = botName;
		this.userService = userService;
		this.participantService = participantService;
	}

	@Override
	public String getBotUsername() {
		return botName;
	}

	@Override
	public void onUpdateReceived(final Update update) {
		final Participant participant = validateParticipant(update);

		if(update.hasMessage() && update.getMessage().hasText() && participant != null ){
			final String messageText = update.getMessage().getText();
			final Long chatId = update.getMessage().getChatId();
			final String answer;

			if(messageText.equals("/start")){
				answer = userService.getInfo();
				sendMessage(chatId, answer);
			}
		}
	}

	private Participant validateParticipant(final Update update){
		final Optional<Participant> participant = participantService.findByName(update.getMessage().getChat().getUserName());
		if(participant.isPresent()){
			return participant.get();
		} else {
			sendMessage(update.getMessage().getChatId(), Constants.HAVE_NO_ACCESS);
			return null;
		}
	}

	private void sendMessage(Long chatId, String textToSend){
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(String.valueOf(chatId));
		sendMessage.setText(textToSend);
		try {
			execute(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
