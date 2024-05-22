package ua.jarvis.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.jarvis.constant.Constants;
import ua.jarvis.model.Participant;
import ua.jarvis.service.ParticipantService;
import ua.jarvis.service.UserService;
import java.io.File;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

			if(messageText.equals("/Загальна інформація.")){
				answer = userService.getInfo();
				sendMessage(chatId, answer);
			}
			if(isPhoneNumber(messageText)){
				final File userPdfFile = userService.findUserByPhoneNumber(messageText);
				sendDocument(chatId, userPdfFile);
			}
		}
	}

	private boolean isPhoneNumber(final String messageText) {
		if (messageText.startsWith("+38")) {
			return true;
		}
		if(startsWithDigits(messageText)){
			return true;
		}

		return false;
	}

	public static boolean startsWithDigits(final String input) {
		final String digitStartPattern = "^\\d";

		final Pattern pattern = Pattern.compile(digitStartPattern);
		final Matcher matcher = pattern.matcher(input);

		return matcher.find();
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

	private void sendDocument(final Long chatId, final File pdf){
		final SendDocument sendDocumentRequest = new SendDocument();
		sendDocumentRequest.setChatId(String.valueOf(chatId));
		sendDocumentRequest.setDocument(new InputFile(pdf));

		try {
			execute(sendDocumentRequest);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void sendMessage(final Long chatId, String textToSend){
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
