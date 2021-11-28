package com.example.hotel.dto;

import lombok.Data;

@Data
public class DtoReserve {
    private long userId;
    private long hotelId;
    private String checkInDate;
    private String checkOutDate;
}
