package tech.pinto.catel.comment.dto;

import lombok.Data;

@Data
public class DtoComment {
    private int id;
    private int userId;
    private int score;
    private String username;
    private String avatar;
    private String title;
    private String content;
}
