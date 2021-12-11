package tech.pinto.catel.room;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.pinto.catel.domain.Room;

import java.util.List;

public interface RepoRoom extends JpaRepository<Room, Long> {
}
