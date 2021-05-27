package com.example.hotel.vo.coupon;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

@Data
@JsonTypeName(value = "Birthday")
public class BirthdayCouponVO extends CouponVO {}
