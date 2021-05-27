package com.example.hotel.bl.coupon;

import com.example.hotel.po.Coupon;
import com.example.hotel.po.Order;

public interface CouponMatchStrategyI {

    /**
     * 检测优惠券对某一订单是否适用
     */
    boolean isMatch(Order order, Coupon coupon);

}
