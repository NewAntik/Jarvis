package com.agency.amazon.configuration;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import com.mongodb.util.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
public class MongoInitializer {

	private static final String PATH = "/Users/antonshapovalov/git/AgencyAmazon/src/main/resources/test_report.json";

	@Value("${spring.data.mongodb.uri}")
	private String mongoConnection;

	private String databaseContent;

	private MongoClient mongoClient;



	@Scheduled(fixedRate = 25000) //30 sec interval
	private void update() {
		try {
			mongoClient = new MongoClient();
			DB database = mongoClient.getDB("amazon");
			DBCollection collection = database.getCollection("users");
			String jsonString = Files.readString(Paths.get(PATH));
			List<DBObject> dbObjects = (List<DBObject>) JSON.parse(jsonString);

			if(databaseContent == null) {
				dbObjects.forEach(collection::insert);
				databaseContent = jsonString;
				System.out.println("Database was populated with: " + jsonString);
			} else if (!databaseContent.equals(jsonString)) {
				dbObjects.forEach(collection::insert);
				databaseContent = jsonString;
				System.out.println("Data base was updated. Content: " + jsonString );
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mongoClient != null) {
				mongoClient.close();
			}
		}
	}
}
