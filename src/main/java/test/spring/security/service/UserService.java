package test.spring.security.service;

import java.util.List;

import test.spring.security.domain.User;

public interface UserService {

	List<User> listUsers();

	void addUser(User user);

}