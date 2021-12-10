package tech.pinto.catel.room;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.pinto.catel.domain.RoomConfig;

public interface RepoRoomConfig extends JpaRepository<RoomConfig, Long> {
}
