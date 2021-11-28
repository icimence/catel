package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategyI;
import com.example.hotel.enums.CouponType;
import com.example.hotel.model.Coupon;
import com.example.hotel.model.Order;
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
