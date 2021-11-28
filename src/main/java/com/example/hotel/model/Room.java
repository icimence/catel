package com.example.hotel.model;

import com.example.hotel.enums.RoomType;
import lombok.Data;

@Data
public class Room {

    private Long id;
    private RoomType roomType;
    private Integer hotelId;
    private Double price;
    private Integer total;
    private Boolean breakfast;
    private Integer peopleMax;

    public Room() {
        breakfast = false;
        peopleMax = 3;
    }

    public Room(int hotelId, int peopleMax, int total, double price, boolean breakfast, RoomType roomType) {
        this.roomType = roomType;
        this.hotelId = hotelId;
        this.price = price;
        this.total = total;
        this.breakfast = breakfast;
        this.peopleMax = peopleMax;
    }

}
