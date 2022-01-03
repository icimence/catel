package tech.pinto.catel.order.dto;

import lombok.Data;

@Data
public class DtoOrderBrief {
    private long id;
    private double price;
    private String hotelName;
    private String hotelAddress;
    private String checkInDate;
    private String checkOutDate;
    private String orderState;
}
