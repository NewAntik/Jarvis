package ua.jarvis.service;

import ua.jarvis.model.User;

import java.io.IOException;
import java.util.List;

public interface UserService {

	List<User> findUsersByPhoneNumber(String phone) throws IOException;

	User findUserByRnokpp(String rnokpp) throws IOException;

	User findUserByPassportNumber(String passportNum);

	User findUserByForeignPassportNumber(String foreingPassportNumber);

	User findUserByThreeNames(String surName, String name, String midlName);

	User findUserBySurNameAndName(String surName, String name);

	User findUserBySurNameAndMidlName(String surName, String midlName);
}
