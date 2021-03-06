package tech.pinto.catel.vo.hotel;

import tech.pinto.catel.hotel.BizRegion;
import tech.pinto.catel.hotel.HotelStar;
import lombok.Data;

import java.util.List;

@Data
public class HotelVO {

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
    private List<RoomVO> rooms;
    private Integer creditBound;
    private String announcement;

}
