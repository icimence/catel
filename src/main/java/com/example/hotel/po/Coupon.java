package com.example.hotel.po;

import com.example.hotel.enums.CouponStatus;
import com.example.hotel.enums.CouponType;
import com.example.hotel.enums.VipType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Coupon {

    private int id;
    private int multiRoomTarget;
    private int hotelId;
    private double targetMoney;
    private double discount;
    private double discountMoney;
    private String description;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private CouponType type;
    private CouponStatus status;
    private VipType level;

    public Coupon() {
        name = "Coupon";
        hotelId = -1;
        status = CouponStatus.Available;
        discount = 1;
        description = "";
        targetMoney = 500;
        discountMoney = 100;
        multiRoomTarget = (int) 1e8;
        startTime = LocalDateTime.now();
        endTime = startTime.plusDays(30);
        level = VipType.Nil;
    }

}
