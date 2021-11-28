package com.example.hotel.model;

import com.example.hotel.enums.UserType;
import com.example.hotel.enums.VipType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {

    private Integer id;
    private String email;
    private String password;
    private String username;
    private Double credit;
    private UserType userType;
    private String avatar;
    private LocalDateTime vipEnd;
    private VipType vipType;

    public User() {
        credit = 100.0;
        avatar = "https://hotel-res-img-public.oss-cn-shanghai.aliyuncs.com/static/user_avatar.jpg";
        userType = UserType.Client;
        username = "default_username";
        password = "default_password";
        vipEnd = LocalDateTime.now();
        vipType = VipType.Small;

    }

}
