package ua.jarvis.service;

import java.io.IOException;

public interface FileFormatter<T, R> {
	T format(R source) throws IOException;
}
