package ua.jarvis.service.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.jarvis.core.model.BaseNameEntity;
import ua.jarvis.core.model.User;
import ua.jarvis.service.FileService;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Component
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

	public void createShortDOCXDocumentAndSend(final Long chatId, final List<User> users) throws IOException, InvalidFormatException {
		final byte [] docxBytes = fileService.createShortDOCXDocument(users);
		sendDocument(chatId, docxBytes, "ПІБ та РНОКПП" + DOCX);
	}

	public void createDOCXDocumentAndSend(final Long chatId, final User user) throws IOException, InvalidFormatException {
		final byte[] docxBytes = fileService.createDOCXFromUser(user);
		final String name = getName(user.getFirstNames());
		final String surName = getName(user.getSurNames());
		final String middleName = getName(user.getMiddleNames());

		sendDocument(chatId, docxBytes, name + "_" + surName + "_" + middleName + DOCX);
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

	private <T extends BaseNameEntity> String getName(final Set<T> names) {
		return names.stream()
			.sorted(Comparator.comparing(BaseNameEntity::getValue))
			.map(BaseNameEntity::getValue)
			.findFirst()
			.orElse("");
	}
}
