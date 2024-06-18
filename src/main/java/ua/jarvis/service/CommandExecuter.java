package ua.jarvis.service;

import java.io.IOException;

public interface CommandExecuter {

	String getType();

	void execute(String text, Long chatId) throws IOException;

}
