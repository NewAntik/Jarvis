package ua.jarvis.service;

import ua.jarvis.model.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
	String getInfo();

	List<User> findUsersByPhoneNumber(final String phone) throws IOException;
}
