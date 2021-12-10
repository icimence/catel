package tech.pinto.catel.coupon;

import tech.pinto.catel.bl.CouponMatchStrategyI;
import tech.pinto.catel.enums.CouponType;
import tech.pinto.catel.domain.Coupon;
import tech.pinto.catel.domain.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TimeCouponStrategy implements CouponMatchStrategyI {

    @Override
    public boolean isMatch(Order order, Coupon coupon) {
        if (coupon.getType() != CouponType.Time) return false;
        LocalDateTime start = coupon.getStartTime();
        LocalDateTime end = coupon.getEndTime();
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(start) && now.isBefore(end);
    }

}
