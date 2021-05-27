package com.example.hotel.vo.user;

import lombok.Data;

@Data
public class UserInfo {

    private Integer id;
    private String password;
    private String username;
    private Double credit;
    private String avatar;

}
