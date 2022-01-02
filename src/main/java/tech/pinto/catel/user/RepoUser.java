package tech.pinto.catel.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import tech.pinto.catel.domain.User;

import javax.transaction.Transactional;
import java.util.Optional;

public interface RepoUser extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String email);

    @Modifying
    @Transactional
    @Query("update User u set u.vipLevel = 0 where u.vipLevel <> 0 and u.vipEnd < current_time ")
    void vipExpire();
}
