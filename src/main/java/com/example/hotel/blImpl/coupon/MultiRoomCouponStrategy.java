package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategyI;
import com.example.hotel.enums.CouponType;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.Order;
import org.springframework.stereotype.Service;

@Service
public class MultiRoomCouponStrategy implements CouponMatchStrategyI {

    @Override
    public boolean isMatch(Order order, Coupon coupon) {
        if (coupon.getType() != CouponType.MultiRoom) return false;
        return order.getRoomNum() >= coupon.getMultiRoomTarget();
    }

}
