package tech.pinto.catel.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import tech.pinto.catel.domain.RoomConfig;
import tech.pinto.catel.domain.RoomUnit;
import tech.pinto.catel.domain.Order;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

public interface RepoRoomUnit extends JpaRepository<RoomUnit, RoomUnit._Id> {
    @Modifying
    @Query(nativeQuery = true, value =
        "update room_unit ru " +
            "join (select room_id from order_room where order_id = :order.id) X " +
            "set number = number + 1 " +
            "where 1=1 " +
            "and ru.date >= :order.checkInDate " +
            "and ru.date < :order.checkOutDate"
    )
        // TODO wrong logic!
    void restoreCanceledRoom(Order order);

    @Query("select ru.number from RoomUnit ru " +
        "where ru.id.roomConfig.id=:configId " +
        "and ru.id.date >= :dateStart " +
        "and ru.id.date < :dateEnd")
    List<Integer> getRoomNumber(long configId, LocalDate dateStart, LocalDate dateEnd);

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
