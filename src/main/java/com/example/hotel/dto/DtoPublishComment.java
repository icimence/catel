package com.example.hotel.dto;

import lombok.Data;

@Data
public class DtoPublishComment {
    private int userId;
    private int orderId;
    private int score;
    private String title;
    private String content;
}
