package ua.jarvis.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoService {
	void savePhoto(MultipartFile multipartFile, String fileName) throws IOException;

	byte[] findPhotoByName(String fileName) throws IOException;
}
