package ua.jarvis.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import ua.jarvis.core.model.User;

import java.io.IOException;
import java.util.List;

public interface FileService {

	byte[] createDOCXFromUser(User user) throws IOException, InvalidFormatException;

	byte[] createShortDOCXDocument(List<User> users) throws IOException, InvalidFormatException;
}
