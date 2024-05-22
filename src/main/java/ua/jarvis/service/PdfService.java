package ua.jarvis.service;

import ua.jarvis.model.User;

import java.io.File;

public interface PdfService {
	File createUserPdf(User user);
}
