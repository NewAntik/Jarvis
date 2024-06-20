package ua.jarvis.service;

import ua.jarvis.model.User;

import java.io.IOException;
import java.util.List;

public interface UserService {

	List<User> findUsersByPhoneNumber(final String phone) throws IOException;

	User findUserByRnokpp(final String rnokpp) throws IOException;

	String getInfo();

	User findUserByPassportNumber(final String passportNum);

	User findUserByForeignPassportNumber(final String foreingPassportNumber);

	User findUserByThreeNames(String surName, String name, String midlName);
}
