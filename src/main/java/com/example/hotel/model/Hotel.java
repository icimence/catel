package com.example.hotel.model;

import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;
import lombok.Data;

@Data
public class Hotel {

    private Integer id;
    private String name;
    private String address;
    private BizRegion bizRegion;
    private HotelStar hotelStar;
    private Double rate;
    private String description;
    private String phoneNumber;
    private Integer managerId;
    private String pic;
    private int creditBound;
    private String announcement;

    public Hotel() {
        name = "";
        address = "";
        bizRegion = BizRegion.XiDan;
        rate = 5.0;
        description = "";
        managerId = -1;
        pic = "https://hotel-res-img-public.oss-cn-shanghai.aliyuncs.com/static/cover.jpg";
        creditBound = 100;
        announcement = "";
    }

}
