package com.example.hotel.dto;

import lombok.Data;

@Data
public class DtoRoomInfo {
    private long roomId;
    private int roomNumber;
    private double price;
    private boolean breakfast;
    private String name;
    private String type;
}
