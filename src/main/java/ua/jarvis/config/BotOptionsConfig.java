package ua.jarvis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Configuration
public class BotOptionsConfig {

	@Bean
	public DefaultBotOptions defaultBotOptions() {
		return new DefaultBotOptions();
	}
}
