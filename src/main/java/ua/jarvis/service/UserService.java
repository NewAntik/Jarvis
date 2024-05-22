package ua.jarvis.service;

import java.io.File;

public interface UserService {
	String getInfo();

	File findUserByPhoneNumber(final String messageText);
}
