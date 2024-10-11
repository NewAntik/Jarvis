package ua.jarvis.service;

import java.io.IOException;

public interface FileFormatterService<T, R> {
	//todo rewrite FileFormatterService to use factory or strategy or another pattern.
	// to provide common methods/vars add abstract class

	T format(R source) throws IOException;
}
