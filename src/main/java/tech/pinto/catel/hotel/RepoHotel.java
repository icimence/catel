package tech.pinto.catel.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import tech.pinto.catel.domain.Hotel;

import javax.transaction.Transactional;

public interface RepoHotel extends JpaRepository<Hotel, Long> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value =
        "update `hotel` h join " +
            "(select hotel_id, min(def_price) min_price from room_config group by hotel_id) rc" +
            " on h.id = rc.hotel_id set h.min_price = rc.min_price where true"
    )
    void refreshMinPrice();

    //    @Update(value = "update hotel.Hotel h set rate=coalesce(" +
    //            "(select x.r from (select hotel_id,avg(score) r from hotel.Comment group by hotel_id) x " +
    //            "where hotel_id=h.id),5) where true")
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update hotel h join " +
        "(select hotel_id, avg(score) r from comment group by hotel_id) c on h.id = c.hotel_id " +
        "set h.rate = c.r where true")
    void freshRate();
}
