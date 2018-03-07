package test.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.spring.security.domain.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long>{

}
