package tech.pinto.catel.order.dto;

import lombok.Data;
import tech.pinto.catel.room.dto.DtoRoomInfo;

import java.util.List;

@Data
public class DtoOrderDetail {
    private long id;
    private long userId;
    private long hotelId;
    private int roomNum;
    private double creditDelta;
    private double price;
    private String hotelName;
    private String checkInDate;
    private String checkOutDate;
    private String createdTime;
    private String orderState;
    private List<DtoRoomInfo> roomInfos;
}
