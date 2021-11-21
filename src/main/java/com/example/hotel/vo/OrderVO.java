package com.example.hotel.vo;

import com.example.hotel.enums.OrderState;
import lombok.Data;

@Data
public class OrderVO {

    private Integer id;
    private Integer userId;
    private Integer personId;
    private Integer hotelId;
    private Integer roomNum;
    private Integer peopleNum;
    private Integer commentId;
    private Double price;
    private Double creditDelta;
    private Boolean haveChild;
    private Boolean breakfast;
    private String hotelName;
    private String hotelAddress;
    private String roomType;
    private String checkInDate;
    private String checkOutDate;
    private String createDate;
    private OrderState orderState;

}
