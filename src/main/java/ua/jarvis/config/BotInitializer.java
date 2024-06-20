package ua.jarvis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ua.jarvis.controller.TelegramBotService;

@Component
public class BotInitializer {

	private final TelegramBotService telegramBotService;

	@Autowired
	public BotInitializer(TelegramBotService telegramBotService) {
		this.telegramBotService = telegramBotService;
	}

	@EventListener({ContextRefreshedEvent.class})
	public void init()throws TelegramApiException{
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
		try{
			telegramBotsApi.registerBot(telegramBotService);
		} catch (TelegramApiException e){
			e.printStackTrace();
		}
	}
}
