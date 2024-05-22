package ua.jarvis.service.impl;

import org.springframework.stereotype.Service;
import ua.jarvis.service.UserService;

import java.io.File;

@Service
public class UserServiceImpl implements UserService {
	@Override
	public String getInfo() {
		return """
            Hello mazafaker!
            Цей телеграм бот знаходиться в розробці.
            В найближчий час буде додан новий функціонал.
            P.s. Для надання доступу до Git,
            необхідно там зареєструватися.
            """;

	}

	@Override
	public File findUserByPhoneNumber(final String messageText) {

		return null;
	}
}
