package com.example.hotel.model;

import com.example.hotel.enums.RoomType;
import lombok.Data;

@Data
public class Room {

    private Long id;
    private RoomType roomType;
    private Integer hotelId;
    private Double price;
    private Boolean breakfast = false;
    private Integer peopleMax = 3;

}
