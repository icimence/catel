package tech.pinto.catel.hotel.dto;

import lombok.Data;

@Data
public class DtoHotelQuery {
    private Integer limit;
    private Integer page;
    private Double filterPriceLower;
    private Double filterPriceUpper;
    private Double filterRate;
    private String filterLocation;
    private String filterInDate;
    private String filterOutDate;
}
