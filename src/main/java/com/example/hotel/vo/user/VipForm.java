package com.example.hotel.vo.user;

import com.example.hotel.enums.VipType;
import lombok.Data;

@Data
public class VipForm {

    int userId;
    int day;
    VipType vipType;

}
