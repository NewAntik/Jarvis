package ua.jarvis.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.jarvis.service.PhotoService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PhotoServiceImpl implements PhotoService {
//	private static final String PHOTO_PATH = "/app/photos/";
	private static final String PHOTO_PATH = "/Users/antonshapovalov/git/JarvisPhoto/";
	@Override
	public void savePhoto(final MultipartFile multipartFile, final String fileName) throws IOException {
		final Path filePath = Paths.get(PHOTO_PATH + fileName);
		Files.write(filePath, multipartFile.getBytes());
	}

	@Override
	public byte[] findPhotoByName(final String fileName) throws IOException {
		final Path filePath = Paths.get(PHOTO_PATH + fileName);
		return Files.readAllBytes(filePath);
	}
}
