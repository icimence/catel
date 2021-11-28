package com.example.hotel.blImpl.hotel;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.example.hotel.bl.hotel.RoomServiceI;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.model.Order;
import com.example.hotel.model.Room;
import com.example.hotel.util.OopsException;
import com.example.hotel.vo.hotel.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService implements RoomServiceI {

    private final RoomMapper roomMapper;

    @Autowired
    public RoomService(RoomMapper roomMapper) {this.roomMapper = roomMapper;}

    @Override
    public void insertRoomInfo(Room room) throws OopsException {
        if (exists(room)) throw new OopsException(7);
        roomMapper.insertRoom(room);
        roomMapper.initRoomNumber(room);
    }

    private boolean exists(Room room) {
        return roomMapper.exists(room);
    }

    @Override
    public void updateRoomNumber(Order order) {

    }

    @Override
    public List<Integer> getRoomNumber(int roomId) {
        return roomMapper.getRoomNumber(roomId);
    }

    @Override
    public List<RoomVO> getByHotel(int id) {
        return roomMapper.selectRoomsByHotelId(id).stream().map(o -> {
            RoomVO roomVO = new RoomVO();
            BeanUtil.copyProperties(o, roomVO, CopyOptions.create().ignoreNullValue());
            return roomVO;
        }).collect(Collectors.toList());
    }

    @Override
    public void removeRoom(int id) {
        roomMapper.removeRoom(id);
        roomMapper.removeRoomNumber(id);
    }

}
