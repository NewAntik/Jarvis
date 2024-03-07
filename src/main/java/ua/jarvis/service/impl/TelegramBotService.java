package ua.jarvis.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.jarvis.service.UserService;

import java.io.IOException;
import java.text.ParseException;

@Service
public class TelegramBotService extends TelegramLongPollingBot {

	private final UserService userService;

	private final String botName;

	@Override
	public String getBotUsername() {
		return botName;
	}

	public TelegramBotService(
		@Value("${bot.name}") final String botName,
		@Value("${bot.token}") final String token,
		final UserService userService
	) {
		super(token);
		this.botName = botName;
		this.userService = userService;
	}

	@Override
	public void onUpdateReceived(Update update) {
		if(update.hasMessage() && update.getMessage().hasText()){
			final String messageText = update.getMessage().getText();
			final Long chatId = update.getMessage().getChatId();
			final String answer;

			if(messageText.equals("/start")){
				answer = userService.getInfo();
				sendMessage(chatId, answer);
			}
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
