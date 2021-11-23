package com.example.hotel.dto;

import lombok.Data;

@Data
public class CreateResidentDTO {
    Integer userId;
    String realName;
    String idNo;
    String phoneNumber;
    String birthday;
}
