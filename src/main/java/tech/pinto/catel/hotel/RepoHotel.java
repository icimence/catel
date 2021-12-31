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

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update hotel_stat hs join " +
        "(select hotel_id, avg(score) r, count(id) cnt from comment group by hotel_id) c " +
        "on hs.hotel_id = c.hotel_id " +
        "set " +
        "hs.rate = round(c.r, 1), " +
        "hs.total = c.cnt " +
        "where true")
    void freshRate();

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update hotel_stat hs join " +
        "(select hotel_id, " +
        "sum(if(score = 1, 1, 0)) s1, " +
        "sum(if(score = 2, 1, 0)) s2, " +
        "sum(if(score = 3, 1, 0)) s3, " +
        "sum(if(score = 4, 1, 0)) s4, " +
        "sum(if(score = 5, 1, 0)) s5 from comment group by hotel_id) scores " +
        "on hs.hotel_id = scores.hotel_id " +
        "set " +
        "hs.score1 = scores.s1, " +
        "hs.score2 = scores.s2, " +
        "hs.score3 = scores.s3, " +
        "hs.score4 = scores.s4, " +
        "hs.score5 = scores.s5 " +
        "where true")
    void freshScoreCount();
}
