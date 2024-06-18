package ua.jarvis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.jarvis.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u JOIN u.phones p WHERE p.number = :number")
	List<User> findByPhoneNumber(@Param("number") String number);

	Optional<User> findUserByRnokpp(String rnokpp);
}
