package ua.jarvis.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.jarvis.model.Participant;
import ua.jarvis.service.ParticipantService;
import ua.jarvis.service.PhoneService;
import ua.jarvis.service.UserService;

import java.io.File;
import java.io.IOException;

@Service
public class TelegramBotService extends TelegramLongPollingBot {

	private final UserService userService;

	private final ParticipantService participantService;

	private final PhoneService phoneService;

	private final String botName;

	private Long chatId;

	public TelegramBotService(
		@Value("${bot.name}") final String botName,
		@Value("${bot.token}") final String token,
		final UserService userService,
		final ParticipantService participantService,
		final PhoneService phoneService
	) {
		super(token);
		this.botName = botName;
		this.userService = userService;
		this.participantService = participantService;
		this.phoneService = phoneService;
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

			final String answer;

			if(messageText.equals("/Загальна інформація.")){
				answer = userService.getInfo();
				sendMessage(answer);
			}
			if(phoneService.isPhoneNumber(messageText)){
				final String normalizedNumber = phoneService.getNormalizedNumber();
				final File userPdfFile;
				try {
					userPdfFile = userService.findUserByPhoneNumber(normalizedNumber);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}

				sendDocument(userPdfFile);
			}
		}
	}

	private void sendDocument(final File pdf){
		final SendDocument sendDocumentRequest = new SendDocument();
		sendDocumentRequest.setChatId(String.valueOf(chatId));
		sendDocumentRequest.setDocument(new InputFile(pdf));

		try {
			execute(sendDocumentRequest);
		} catch (TelegramApiException e) {
			e.printStackTrace();
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
