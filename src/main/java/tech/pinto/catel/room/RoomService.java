package tech.pinto.catel.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.pinto.catel.domain.Room;
import tech.pinto.catel.room.dto.DtoConfigInfo;
import tech.pinto.catel.util.MapX;
import tech.pinto.catel.util.OopsException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final MapperRoom mapperRoom;
    private final RepoRoomConfig repoRoomConfig;
    private final MapX mapX;

    @Autowired
    public RoomService(MapperRoom mapperRoom, RepoRoomConfig repoRoomConfig, MapX mapX) {
        this.mapperRoom = mapperRoom;
        this.repoRoomConfig = repoRoomConfig;
        this.mapX = mapX;
    }

    public void insertRoomInfo(Room room) throws OopsException {
        if (exists(room)) throw new OopsException(7);
        mapperRoom.insertRoom(room);
        mapperRoom.initRoomNumber(room);
    }

    private boolean exists(Room room) {
        return mapperRoom.exists(room);
    }

    public List<Integer> getRoomNumber(int roomId) {
        return mapperRoom.getRoomNumber(roomId);
    }

    public List<DtoConfigInfo> getByHotel(long id) {
        var configs = repoRoomConfig.findByHotelId(id);
        return configs.stream().map(mapX::toInfo).collect(Collectors.toList());
    }

    public void removeRoom(int id) {
        mapperRoom.removeRoom(id);
        mapperRoom.removeRoomNumber(id);
    }

}
