package ua.jarvis.service.executor;

import ua.jarvis.core.model.enums.ExecutorType;

import java.io.IOException;

public interface CommandExecutorService {

	ExecutorType getType();

	void execute(String text, Long chatId) throws IOException;

}
