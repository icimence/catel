package tech.pinto.catel.user;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.pinto.catel.domain.User;

import java.util.Optional;

public interface RepoUser extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String email);
}
