package test.spring.security.service;

import java.util.List;

import test.spring.security.domain.User;
import test.spring.security.web.form.UserQueryForm;

public interface UserService {

	List<User> listUsers();

	void addUser(User user);

	List<User> listUsers(UserQueryForm user);

}