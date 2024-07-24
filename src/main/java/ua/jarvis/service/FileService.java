package ua.jarvis.service;

import ua.jarvis.core.model.User;

import java.io.File;
import java.io.IOException;

public interface FileService {
	File createUserPdf(User user) throws IOException;

	byte[] createDOCXFromUser(User user) throws IOException;
}
