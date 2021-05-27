package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategyI;
import com.example.hotel.enums.CouponType;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.Order;
import org.springframework.stereotype.Service;

@Service
public class TargetMoneyCouponStrategy implements CouponMatchStrategyI {

    @Override
    public boolean isMatch(Order order, Coupon coupon) {
        if (coupon.getType() != CouponType.Target) return false;
        return order.getPrice() >= coupon.getTargetMoney();
    }

}
