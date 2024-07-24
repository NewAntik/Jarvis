package ua.jarvis.service.executor;

import java.io.IOException;

public interface CommandExecutorService {

	String getType();

	void execute(String text, Long chatId) throws IOException;

}
