package ua.jarvis.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ua.jarvis.model.Phone;
import ua.jarvis.model.User;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
	public static final String PHONE_NUMBER = "1234567890";

	@Autowired
	private UserRepository userRepository;

	@Test
	void findUserByPhoneNumber_ShouldReturnUser() {
		final User result = userRepository.findUserByPhoneNumber(PHONE_NUMBER).get();

		assertTrue(result.getPhones()
				.stream()
				.map(Phone::getPhoneNumber)
				.anyMatch(phoneNumber -> phoneNumber.equals(PHONE_NUMBER)));
	}
}