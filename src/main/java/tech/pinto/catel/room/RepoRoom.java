package tech.pinto.catel.room;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.pinto.catel.domain.Room;

public interface RepoRoom extends JpaRepository<Room, Long> {
}
