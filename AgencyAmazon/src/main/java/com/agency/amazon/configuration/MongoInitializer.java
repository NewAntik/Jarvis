package com.agency.amazon.configuration;

import com.agency.amazon.model.User;
import com.agency.amazon.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class MongoInitializer {
	private static final Logger LOG = LoggerFactory.getLogger(MongoInitializer.class);

	private static final String PATH = "/Users/antonshapovalov/git/AgencyAmazon/src/main/resources/test_report.json";

	private String databaseContent;

	private final UserRepository userRepository;

	private final CacheManager cacheManager;

	public MongoInitializer(UserRepository userRepository, CacheManager cacheManager) {
		this.userRepository = userRepository;
		this.cacheManager = cacheManager;
	}

	@Scheduled(fixedRate = 25000) //30 sec interval
	private void update() {
		try {
			String jsonString = Files.readString(Paths.get(PATH));
			ObjectMapper objectMapper = new ObjectMapper();
			List<User> users = Arrays.asList(objectMapper.readValue(jsonString, User[].class));

			if(databaseContent == null) {
				userRepository.saveAll(users);
				databaseContent = jsonString;
			} else if (!databaseContent.equals(jsonString)) {
				userRepository.saveAll(users);
				databaseContent = jsonString;
				LOG.info("Data base was updated. Content:[{}] " + jsonString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
