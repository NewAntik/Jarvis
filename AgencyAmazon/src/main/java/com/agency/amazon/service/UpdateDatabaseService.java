package com.agency.amazon.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Service
public class UpdateDatabaseService {

	private final ObjectMapper objectMapper;

	private final MongoTemplate mongoTemplate;

	public UpdateDatabaseService(final ObjectMapper objectMapper, final MongoTemplate mongoTemplate) {
		this.objectMapper = objectMapper;
		this.mongoTemplate = mongoTemplate;
	}

	@Scheduled(fixedRate = 60000)// every minute
	public void updateDatabasePeriodically() {
		try {
			Map<String, Object> json = readJsonFile();
			mongoTemplate.dropCollection("users");
			mongoTemplate.save(json, "users");

			System.out.println("Data has been updated in MongoDB users collection.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Map<String, Object> readJsonFile() throws IOException {
		return objectMapper.readValue(new File("/Users/antonshapovalov/git/AgencyAmazon/src/main/resources/test_report.json"), Map.class);
	}
}
