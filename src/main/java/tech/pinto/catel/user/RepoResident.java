package tech.pinto.catel.user;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.pinto.catel.domain.Resident;

import java.util.List;

public interface RepoResident extends JpaRepository<Resident, Long> {
    List<Resident> findByOwnerId(long id);
}
