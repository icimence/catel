package com.example.hotel.bl.coupon;

import com.example.hotel.enums.CouponType;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.Order;

import java.util.List;

public interface CouponServiceI {

    /**
     * 返回某一订单可用的优惠策略列表
     */
    List<Coupon> getMatchOrderCoupon(Order order);

    /**
     * 查看某个酒店提供的所有优惠策略（包括失效的）
     */
    List<Coupon> getByHotel(Integer hotelId);

    void addCoupon(Coupon coupon);

    List<Coupon> getByType(CouponType couponType);

    void remove(int id);

    List<Coupon> getGlobal();

}
