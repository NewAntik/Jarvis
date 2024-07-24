package ua.jarvis.facade;

import java.io.IOException;

public interface StrategyFacade {
	void execute(String text, Long chatId) throws IOException;
}
