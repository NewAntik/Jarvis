package ua.jarvis.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.jarvis.model.Participant;
import ua.jarvis.model.User;
import ua.jarvis.service.FileService;
import ua.jarvis.service.ParticipantService;
import ua.jarvis.service.PhoneService;
import ua.jarvis.service.UserService;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

@Service
public class TelegramBotService extends TelegramLongPollingBot {
	private static final Logger LOG = LoggerFactory.getLogger(TelegramBotService.class);

	private final UserService userService;

	private final ParticipantService participantService;

	private final PhoneService phoneService;

	private final String botName;

	private Long chatId;

	private final FileService fileService;

	public TelegramBotService(
		@Value("${bot.name}") final String botName,
		@Value("${bot.token}") final String token,
		final UserService userService,
		final ParticipantService participantService,
		final PhoneService phoneService,
		final FileService fileService
	) {
		super(token);
		this.botName = botName;
		this.userService = userService;
		this.participantService = participantService;
		this.phoneService = phoneService;
		this.fileService = fileService;
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
			try {
				if (messageText.equals("/Загальна інформація.")) {
					LOG.info("Received about info document method was called by: {}", participant.getName());
					answer = userService.getInfo();

					sendMessage(answer);
				}
				if (phoneService.isPhoneNumber(messageText)) {
					LOG.info("Received user info document method was called by: {}", participant.getName());

					final String normalizedNumber = phoneService.getNormalizedNumber();
					final List<User> users = userService.findUsersByPhoneNumber(normalizedNumber);
					for(User user : users){
						final byte [] docxBytes = fileService.createDOCXFromUser(user);
						sendDocument(docxBytes, user.getSurName() + "_" + user.getName() + "_" + user.getMidlName() + ".docx");
					}
								}
			} catch (Throwable e){
				LOG.error("An error occurred while processing the update", e);
				sendMessage(e.getMessage());
			}
		}
	}

	private void sendDocument(final byte[] docBytes, final String fileName) {
		final SendDocument sendDocumentRequest = new SendDocument();
		sendDocumentRequest.setChatId(String.valueOf(chatId));
		final InputStream inputStream = new ByteArrayInputStream(docBytes);
		final InputFile inputFile = new InputFile(inputStream, fileName);

		sendDocumentRequest.setDocument(inputFile);

		try {
			execute(sendDocumentRequest);
		} catch (TelegramApiException e) {
			e.printStackTrace();
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
