package ua.jarvis.service;

import java.io.File;
import java.io.IOException;

public interface UserService {
	String getInfo();

	File findUserByPhoneNumber(final String messageText) throws IOException;
}
