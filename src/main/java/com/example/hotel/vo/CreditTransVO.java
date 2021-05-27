package com.example.hotel.vo;

import com.example.hotel.enums.OrderState;
import lombok.Data;

@Data
public class CreditTransVO {

    private Integer id;
    private String hotelName;
    private OrderState orderState;
    private Double creditDelta;

}
