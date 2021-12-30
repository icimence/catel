package tech.pinto.catel.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import tech.pinto.catel.domain.RoomConfig;
import tech.pinto.catel.domain.RoomUnit;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

public interface RepoRoomUnit extends JpaRepository<RoomUnit, RoomUnit._Id> {
    @Modifying
    @Query(value =
        "update RoomUnit ru " +
            "set ru.number = ru.number + :number " +
            "where ru.id.roomConfig = :config " +
            "and ru.id.date >= :in " +
            "and ru.id.date < :out"
    )
    void restoreCanceledRoom(RoomConfig config, int number, LocalDate in, LocalDate out);

    @Query("select ru from RoomUnit ru " +
        "where ru.id.roomConfig.id=:configId " +
        "and ru.id.date >= :dateStart " +
        "and ru.id.date < :dateEnd")
    List<RoomUnit> relatedUnits(long configId, LocalDate dateStart, LocalDate dateEnd);

    @Modifying
    @Transactional
    @Query("update RoomUnit ru " +
        "set ru.number = ru.number - :number " +
        "where ru.id.roomConfig = :config " +
        "and ru.id.date >= :in " +
        "and ru.id.date < :out ")
    void invalidOccupied(RoomConfig config, int number, LocalDate in, LocalDate out);

    @Modifying
    @Transactional
    @Query("delete from RoomUnit ru " +
        "where ru.id.date = current_date")
    void dailyUpdateRemove();
}
