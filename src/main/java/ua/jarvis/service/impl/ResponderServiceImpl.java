package ua.jarvis.service.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.jarvis.core.model.User;
import ua.jarvis.service.FileService;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ResponderServiceImpl extends DefaultAbsSender {
	private static final Logger LOG = LoggerFactory.getLogger(ResponderServiceImpl.class);

	private static final String DOCX = ".docx";

	private final FileService fileService;

	public ResponderServiceImpl(
		final DefaultBotOptions options,
		@Value("${bot.token}") final String token,
		final FileService fileService
	) {
		super(options, token);
		this.fileService = fileService;
	}

	public void createDOCXDocumentAndSend(final Long chatId, final User user) throws IOException, InvalidFormatException {
		final byte [] docxBytes = fileService.createDOCXFromUser(user);
		sendDocument(chatId, docxBytes, user.getSurName() + "_" + user.getName() + "_" + user.getMiddleName() + DOCX);
	}

	public void sendDocument(final Long chatId, final byte[] docBytes, final String fileName) {
		final SendDocument sendDocumentRequest = new SendDocument();
		sendDocumentRequest.setChatId(String.valueOf(chatId));
		final InputStream inputStream = new ByteArrayInputStream(docBytes);
		final InputFile inputFile = new InputFile(inputStream, fileName);
		sendDocumentRequest.setDocument(inputFile);

		try {
			execute(sendDocumentRequest);
		} catch (final TelegramApiException e) {
			LOG.error("Failed to send document", e);
		}
	}

	public void sendDocument(final Long chatId, final File pdf) {
		final SendDocument sendDocumentRequest = new SendDocument();
		sendDocumentRequest.setChatId(String.valueOf(chatId));
		sendDocumentRequest.setDocument(new InputFile(pdf));

		try {
			execute(sendDocumentRequest);
		} catch (final TelegramApiException e) {
			LOG.error("Failed to send document", e);
		}
	}

	public void sendMessage(final Long chatId, final String textToSend) {
		final SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(String.valueOf(chatId));
		sendMessage.setText(textToSend);
		try {
			execute(sendMessage);
		} catch (final TelegramApiException e) {
			LOG.error("Failed to send message", e);
		}
	}
}
