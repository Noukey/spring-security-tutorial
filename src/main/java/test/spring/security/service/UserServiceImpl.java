package test.spring.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import test.spring.security.domain.User;
import test.spring.security.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService,UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	/* (non-Javadoc)
	 * @see test.spring.security.service.UserService#listUsers()
	 */
	@Override
	public List<User> listUsers() {
		List<User> users = userRepository.findAll();
		return users;
	}
	
	@Override
	public void addUser(User user) {
		userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}
}
