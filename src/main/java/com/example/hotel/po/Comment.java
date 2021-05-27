package com.example.hotel.po;

import lombok.Data;

@Data
public class Comment {

    private int id;
    private int userId;
    private int hotelId;
    private int score;
    private String title;
    private String content;

    public Comment() {
        title = "";
        content = "";
    }

}
