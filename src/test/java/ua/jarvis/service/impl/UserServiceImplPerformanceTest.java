package ua.jarvis.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.jarvis.core.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceImplPerformanceTest {
	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImplPerformanceTest.class);

	private final List<User> users = new ArrayList<>();
	private static final int LARGE_DATA_SIZE = 100_000; // Adjust size as needed

	@BeforeEach
	public void setup() {
		// Generate large test data
		for (int i = 0; i < LARGE_DATA_SIZE; i++) {
			User user = new User();
			user.setRnokpp(UUID.randomUUID().toString()); // Unique identifier for testing
			users.add(user);
		}

		// Add some duplicates for realism
		for (int i = 0; i < LARGE_DATA_SIZE / 10; i++) {
			users.get(i).setRnokpp(users.get(i + 1).getRnokpp());
		}
	}

	@Test
	public void testStreamFilteringPerformance() {
		Set<String> rnokpps = new HashSet<>();

		long startTime = System.currentTimeMillis();

		List<User> filteredUsers = users.stream()
			.filter(user -> rnokpps.add(user.getRnokpp()))
			.toList();

		long endTime = System.currentTimeMillis();
		LOG.info("Stream filtering took {} ms", (endTime - startTime));

		// Verify filtered list size (optional based on your needs)
		assertEquals(filteredUsers.size(), rnokpps.size());
	}

	@Test
	public void testForLoopFilteringPerformance() {
		Set<String> rnokpps = new HashSet<>();
		List<User> filteredUsers = new ArrayList<>();

		long startTime = System.currentTimeMillis();

		for (User user : users) {
			if (rnokpps.add(user.getRnokpp())) {
				filteredUsers.add(user);
			}
		}

		long endTime = System.currentTimeMillis();
		LOG.info("For-loop filtering took {} ms", (endTime - startTime));

		// Verify filtered list size (optional based on your needs)
		assertEquals(filteredUsers.size(), rnokpps.size());
	}
}