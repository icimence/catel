package com.example.hotel.dto;

import lombok.Data;

@Data
public class CreateOrderVO {

    private Integer userId;
    private Integer personId;
    private Integer hotelId;
    private String roomType;
    private Integer roomNum;
    private Integer peopleNum;
    private Boolean breakfast;
    private String hotelName;
    private String hotelAddress;
    private String checkInDate;
    private String checkOutDate;

}
