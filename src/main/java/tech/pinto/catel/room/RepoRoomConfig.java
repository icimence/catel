package tech.pinto.catel.room;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.pinto.catel.domain.RoomConfig;

import java.util.List;

public interface RepoRoomConfig extends JpaRepository<RoomConfig, Long> {
    List<RoomConfig> findByHotelId(long id);
}
