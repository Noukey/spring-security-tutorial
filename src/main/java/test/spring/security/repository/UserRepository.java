package test.spring.security.repository;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import test.spring.security.domain.User;

public interface UserRepository  extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>{
	User findByUsername(String username);
	Stream<User> findByUsernameLike(String username);
}
