package ua.jarvis.service;

import ua.jarvis.model.User;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface UserService {

	List<User> findUsersByPhoneNumber(String phone) throws IOException;

	User findUserByRnokpp(String rnokpp) throws IOException;

	User findUserByPassportNumber(String passportNum);

	User findUserByForeignPassportNumber(String foreingPassportNumber);

	User findUserByThreeNames(String surName, String name, String midlName);

	User findUserBySurNameAndName(String surName, String name);

	User findUserBySurNameAndMidlName(String surName, String midlName);

	List<User> findUserByThreeNamesAndDate(
		String surName, String name, String midlName, String day, String month, String year
	);

	List<User> findUserByNameMidlNameAndDate(
		String name, String midlName, String day, String month, String year
	);

	List<User> findUserBySurNameMidlNameAndDate(
		String surName, String midlName, String day, String month, String year
	);

	List<User> findUserBySurNameNameAndDate(
		String surName, String name, String day, String month, String year
	);
}
