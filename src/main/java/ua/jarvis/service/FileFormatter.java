package ua.jarvis.service;

import com.itextpdf.layout.element.IBlockElement;
import ua.jarvis.model.User;

import java.io.IOException;
import java.util.List;

public interface FileFormatter {
	List<IBlockElement> format(User user) throws IOException;
}
