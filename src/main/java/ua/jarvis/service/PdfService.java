package ua.jarvis.service;

import ua.jarvis.model.User;

import java.io.File;
import java.io.IOException;

public interface PdfService {
	File createUserPdf(User user) throws IOException;
}
