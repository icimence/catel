package com.example.hotel.po;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Person {

    private int id;
    private int userId;
    private String realName;
    private String idNo;
    private String phoneNumber;
    private LocalDate birthday;

    public Person() {
        realName = "default_real_name";
        phoneNumber = "12345678910";
    }

}
