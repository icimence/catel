package tech.pinto.catel.model;

import lombok.Data;

@Data
public class Comment {

    private Long id;
    private int userId;
    private int hotelId;
    private int score;
    private String title;
    private String content;

}
