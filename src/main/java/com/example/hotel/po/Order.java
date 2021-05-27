package com.example.hotel.po;

import com.example.hotel.enums.OrderState;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Order {

    private int id;
    private int userId;
    private int personId;
    private int hotelId;
    private int commentId;
    private int roomNum;
    private int peopleNum;
    private double creditDelta;
    private double price;
    private Boolean haveChild;
    private Boolean breakfast;
    private String roomType;
    private String hotelName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime createDate;
    private OrderState orderState;

    public Order() {
        breakfast = false;
        createDate = LocalDateTime.now();
        orderState = OrderState.Available;
        commentId = -1;
        breakfast = false;
        creditDelta = 0;
        hotelName = "";
    }

}
