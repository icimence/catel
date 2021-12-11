package tech.pinto.catel.room;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import tech.pinto.catel.domain.Room;
import tech.pinto.catel.domain.Order;
import tech.pinto.catel.util.OopsException;
import tech.pinto.catel.vo.hotel.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final MapperRoom mapperRoom;

    @Autowired
    public RoomService(MapperRoom mapperRoom) {this.mapperRoom = mapperRoom;}

    public void insertRoomInfo(Room room) throws OopsException {
        if (exists(room)) throw new OopsException(7);
        mapperRoom.insertRoom(room);
        mapperRoom.initRoomNumber(room);
    }

    private boolean exists(Room room) {
        return mapperRoom.exists(room);
    }

    public void updateRoomNumber(Order order) {

    }

    public List<Integer> getRoomNumber(int roomId) {
        return mapperRoom.getRoomNumber(roomId);
    }

    public List<RoomVO> getByHotel(int id) {
        return mapperRoom.selectRoomsByHotelId(id).stream().map(o -> {
            RoomVO roomVO = new RoomVO();
            BeanUtil.copyProperties(o, roomVO, CopyOptions.create().ignoreNullValue());
            return roomVO;
        }).collect(Collectors.toList());
    }

    public void removeRoom(int id) {
        mapperRoom.removeRoom(id);
        mapperRoom.removeRoomNumber(id);
    }

}
