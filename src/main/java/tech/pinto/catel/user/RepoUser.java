package tech.pinto.catel.user;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.pinto.catel.domain.User;

public interface RepoUser extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String email);
}
