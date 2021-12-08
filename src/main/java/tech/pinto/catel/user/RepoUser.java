package tech.pinto.catel.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoUser extends JpaRepository<User, Long> {
}
