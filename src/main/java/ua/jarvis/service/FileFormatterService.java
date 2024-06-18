package ua.jarvis.service;

import java.io.IOException;

public interface FileFormatterService<T, R> {
	T format(R source) throws IOException;
}
