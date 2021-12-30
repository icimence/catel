package tech.pinto.catel.hotel.dto;

import lombok.Data;
import tech.pinto.catel.room.dto.DtoConfigInfo;

import java.util.List;

@Data
public class DtoHotelDetail {
    private long id;
    private int creditBound;
    private String bizRegion;
    private String hotelStar;
    private String description;
    private String phoneNumber;
    private String name;
    private String address;
    private String landscape;
    private String portrait;
    private String announcement;
    private List<DtoConfigInfo> rooms;
    private double rate;
    private double[] rateDist;
}
