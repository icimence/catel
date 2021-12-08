package tech.pinto.catel.vo;

import lombok.Data;

@Data
public class CommentVO {

    private int id;
    private int userId;
    private int hotelId;
    private int score;
    private int orderId;
    private String username;
    private String avatar;
    private String title;
    private String content;

}
