package ua.jarvis.service.executer;

import java.io.IOException;

public interface CommandExecuterService {

	String getType();

	void execute(String text, Long chatId) throws IOException;

}
