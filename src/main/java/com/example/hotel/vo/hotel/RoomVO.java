package com.example.hotel.vo.hotel;

import lombok.Data;

@Data
public class RoomVO {

    private Integer id;
    private String roomType;
    private Double price;
    private Integer total;
    private Boolean breakfast;
    private Integer peopleMax;

}
