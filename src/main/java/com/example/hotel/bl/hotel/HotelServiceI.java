package com.example.hotel.bl.hotel;

import com.example.hotel.po.Hotel;
import com.example.hotel.vo.hotel.HotelVO;

import java.util.List;

public interface HotelServiceI {

    /**
     * 添加酒店
     */
    void addHotel(HotelVO hotelVO);

    /**
     * 列表获取酒店信息
     */
    List<HotelVO> retrieveHotels();

    /**
     * 获取某家酒店详细信息
     */
    HotelVO selectById(Integer hotelId);

    /**
     * 得到某位经理所管理的酒店列表
     */
    List<Hotel> getHotelByManager(Integer id);

    /**
     * 更新指定酒店的信息
     */
    void updateHotelInfo(HotelVO hotelVO);

    /**
     * 得到所有未绑定的酒店
     */
    List<Hotel> unboundHotel();

    List<Hotel> fzySearch(String keyword);

    List<HotelVO> getHot();

}
