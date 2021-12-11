package tech.pinto.catel.hotel.dto;

import lombok.Data;
import tech.pinto.catel.room.dto.DtoConfigInfo;

import java.util.List;

@Data
public class DtoHotelDetail {
    private long id;
    private int creditBound;
    private double rate;
    private String bizRegion;
    private String hotelStar;
    private String description;
    private String phoneNumber;
    private String name;
    private String address;
    private String pic;
    private String announcement;
    private List<DtoConfigInfo> rooms;
}
