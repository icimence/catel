package com.example.hotel.vo.coupon;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

@Data
@JsonTypeName(value = "MultiRoom")
public class MultiRoomCouponVO extends CouponVO {

    private Integer multiRoomTarget;

}
