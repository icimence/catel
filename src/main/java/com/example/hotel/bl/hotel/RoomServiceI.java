package com.example.hotel.bl.hotel;

import com.example.hotel.model.Order;
import com.example.hotel.model.Room;
import com.example.hotel.util.OopsException;
import com.example.hotel.vo.hotel.RoomVO;

import java.util.List;

public interface RoomServiceI {

    /**
     * 添加酒店客房信息
     */
    void insertRoomInfo(Room room) throws OopsException;

    /**
     * 预订酒店后更新客房房间数量
     */
    void updateRoomNumber(Order order);

    /**
     * 获取指定订单所需房间剩余数量
     */
    List<Integer> getRoomNumber(int roomId);

    /**
     * 获取某个酒店的全部房间信息
     */
    List<RoomVO> getByHotel(int id);

    void removeRoom(int id);

}
