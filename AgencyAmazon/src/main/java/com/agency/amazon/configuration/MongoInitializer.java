package com.agency.amazon.configuration;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import com.mongodb.util.JSON;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class MongoInitializer implements CommandLineRunner {

	private static final String PATH = "/Users/antonshapovalov/git/AgencyAmazon/src/main/resources/test_report.json";

	@Value("${spring.data.mongodb.uri}")
	private String mongoConnection;

	private String lastJsonContent;

	private MongoClient mongoClient;

	@Override
	public void run(String... args) {
		try {
			mongoClient = new MongoClient();
			DB database = mongoClient.getDB("amazon");
			DBCollection collection = database.getCollection("users");
			String jsonString = Files.readString(Paths.get(PATH));

			List<DBObject> dbObjects = (List<DBObject>) JSON.parse(jsonString);

			// Insert each DBObject into the collection
			for (DBObject dbObject : dbObjects) {
				collection.insert(dbObject);
			}

//			DBObject dbObject = (DBObject) JSON.parse(jsonString);
//
//			// Insert the DBObject into the collection
//			collection.insert(dbObject);

			System.out.println(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mongoClient != null) {
				mongoClient.close();
			}
		}
	}

	@Scheduled(fixedRate = 25000) //30 sec interval
	public void checkForChanges() throws Exception {
		String json = new String(Files.readAllBytes(Paths.get("test_report.json")));
		if (json.equals(lastJsonContent)) {
			System.out.println("No changes in the file.");
			return;
		}

		run();
	}
}
