package ua.jarvis.service;

import ua.jarvis.model.User;

import java.io.File;
import java.io.IOException;

public interface UserService {
	String getInfo();

	User findUserByPhoneNumber(final String messageText) throws IOException;
}
