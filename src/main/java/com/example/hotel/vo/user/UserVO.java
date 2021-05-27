package com.example.hotel.vo.user;

import com.example.hotel.enums.UserType;
import com.example.hotel.enums.VipType;
import lombok.Data;

@Data
public class UserVO {

    private Integer id;
    private String email;
    private String password;
    private String username;
    private Double credit;
    private UserType userType;
    private String avatar;
    private String vipEnd;
    private VipType vipType;

}
