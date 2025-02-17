package ua.jarvis.service;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import ua.jarvis.core.model.User;

import java.io.IOException;
import java.util.List;

public interface FileFormatterService {

	List<XWPFParagraph> format(User user) throws IOException;
}
