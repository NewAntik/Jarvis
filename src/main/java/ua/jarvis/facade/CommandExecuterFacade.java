package ua.jarvis.facade;

import java.io.IOException;

public interface CommandExecuterFacade {
	void execute(String text, Long chatId) throws IOException;
}
