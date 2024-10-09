package ua.jarvis.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import ua.jarvis.core.model.User;

import java.io.File;
import java.io.IOException;

public interface FileService {
	File createUserPdf(User user) throws IOException;

	byte[] createDOCXFromUser(User user) throws IOException, InvalidFormatException;
}
