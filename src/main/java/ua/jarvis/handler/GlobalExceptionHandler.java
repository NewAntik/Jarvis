package ua.jarvis.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.jarvis.service.impl.TelegramBotService;

@ControllerAdvice
public class GlobalExceptionHandler {

	private final TelegramBotService botService;

	public GlobalExceptionHandler(final TelegramBotService botService) {
		this.botService = botService;
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public void handleAccessAuthenticationException(final IllegalArgumentException e) {
		botService.sendMessage(e.getMessage());
	}
}
