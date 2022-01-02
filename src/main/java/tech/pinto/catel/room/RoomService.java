package tech.pinto.catel.room;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.pinto.catel.domain.QRoomConfig;
import tech.pinto.catel.domain.QRoomUnit;
import tech.pinto.catel.domain.Room;
import tech.pinto.catel.room.dto.DtoConfigInfo;
import tech.pinto.catel.room.dto.DtoScreenRoomConfig;
import tech.pinto.catel.util.MapX;
import tech.pinto.catel.util.error.OopsException;
import tech.pinto.catel.util.UtilDate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final MapperRoom mapperRoom;
    private final RepoRoomConfig repoRoomConfig;
    private final MapX mapX;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public RoomService(MapperRoom mapperRoom, RepoRoomConfig repoRoomConfig, MapX mapX, JPAQueryFactory queryFactory) {
        this.mapperRoom = mapperRoom;
        this.repoRoomConfig = repoRoomConfig;
        this.mapX = mapX;
        this.queryFactory = queryFactory;
    }

    public void insertRoomInfo(Room room) throws OopsException {
        if (exists(room)) throw new OopsException(7);
        mapperRoom.insertRoom(room);
        mapperRoom.initRoomNumber(room);
    }

    private boolean exists(Room room) {
        return mapperRoom.exists(room);
    }

    public List<Integer> getRoomNumber(long roomId) {
        return mapperRoom.getRoomNumber(roomId);
    }

    public List<DtoConfigInfo> getByHotel(long id) {
        var configs = repoRoomConfig.findByHotelId(id);
        return configs.stream().map(mapX::toInfo).collect(Collectors.toList());
    }

    public void removeRoom(long id) {
        mapperRoom.removeRoom(id);
        mapperRoom.removeRoomNumber(id);
    }

    public DtoConfigInfo information(long id) throws OopsException {
        var config = repoRoomConfig.findById(id);
        if (config.isEmpty()) throw new OopsException(11);
        var r = mapX.toInfo(config.get());
        System.out.println(r);
        return r;
    }

    public List<DtoConfigInfo> screen(DtoScreenRoomConfig dtoScreenRoomConfig) {
        var qrc = QRoomConfig.roomConfig;
        var qru = QRoomUnit.roomUnit;
        var inDate = UtilDate.from(dtoScreenRoomConfig.getInDate());
        var outDate = UtilDate.from(dtoScreenRoomConfig.getOutDate());
        var duration = outDate.toEpochDay() - inDate.toEpochDay();

        System.out.println(dtoScreenRoomConfig);
        
        return queryFactory
            .select(qrc)
            .from(qrc)
            .join(qru)
            .on(qru.id.roomConfig.eq(qrc))
            .where(qru.id.date.goe(inDate), qru.id.date.lt(outDate))
            .where(qru.number.goe(dtoScreenRoomConfig.getRoomNumber()))
            .where(qrc.hotel.id.eq(dtoScreenRoomConfig.getId()))
            .groupBy(qrc)
            .having(qru.count().eq(duration))
            .fetch()
            .stream()
            .map(mapX::toInfo)
            .collect(Collectors.toList());

    }
}
