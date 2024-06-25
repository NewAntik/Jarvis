package ua.jarvis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.jarvis.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

	Optional<User> findUserByRnokpp(String rnokpp);

	Optional<User> findUserBySurNameAndName(String surName, String name);

	Optional<User> findUserBySurNameAndMidlName(String surName, String midlName);

	@Query("SELECT u FROM User u JOIN u.phones p WHERE p.number = :number")
	List<User> findUsersByPhoneNumber(@Param("number") String number);

	@Query("SELECT u FROM User u JOIN u.passports p WHERE p.passportNumber = :passportNumber")
	Optional<User> findUserByPassportNumber(@Param("passportNumber") String passportNumber);

	@Query("SELECT u FROM User u JOIN u.foreignPassports p WHERE p.passportNumber = :foreignPassportNumber")
	Optional<User> findUserByForeignPassportNumber(@Param("foreignPassportNumber") String foreignPassportNumber);

	@Query("SELECT u FROM User u WHERE u.surName = :surName AND u.name = :name AND u.midlName = :midlName")
	Optional<User> findUserByThreeNames(
		@Param("surName") String surName,
		@Param("name") String name,
		@Param("midlName") String midlName
	);
}
