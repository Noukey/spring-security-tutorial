package test.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import test.spring.security.domain.User;

public interface UserRepository  extends JpaRepository<User, Long>{
	User findByUsername(String username);
}
