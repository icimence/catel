package tech.pinto.catel.coupon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.pinto.catel.domain.CouponBase;

import java.util.List;

public interface RepoCoupon extends JpaRepository<CouponBase, Long> {
    @Query("select cb from CouponBase cb where " +
        "cb.owner.id=:userId and cb.hotel.id=:hotelId " +
        "and cb.timeEnabled <= current_date " +
        "and cb.timeOutdated > current_date ")
    List<CouponBase> getUsable(long userId, long hotelId);
}
