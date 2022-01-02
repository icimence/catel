package tech.pinto.catel.hotel.dto;

import lombok.Data;

@Data
public class DtoHotelBrief {
    private long id;
    private String name;
    private String address;
    private double rate;
    private double minPrice;
    private String landscape;
    private String portrait;
    private String star;
}
