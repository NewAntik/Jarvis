package ua.jarvis.service;

import ua.jarvis.core.model.User;
import ua.jarvis.core.model.criteria.UserCriteria;

import java.util.List;

public interface UserService {

	List<User> findUsersByCriteria(UserCriteria criteria);
}
