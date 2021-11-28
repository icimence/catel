package com.example.hotel.dto;

import lombok.Data;

@Data
public class DtoOrderDetail {
    private long id;
    private long userId;
    private long hotelId;
    private int roomNum;
    private double creditDelta;
    private double price;
    private String hotelName;
    private String checkInDate;
    private String checkOutDate;
    private String createdTime;
    private String orderState;
    private DtoRoomInfo roomInfo;
}
