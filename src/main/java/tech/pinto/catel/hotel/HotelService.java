package tech.pinto.catel.hotel;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import tech.pinto.catel.enums.BizRegion;
import tech.pinto.catel.room.*;
import tech.pinto.catel.order.MapperOrder;
import tech.pinto.catel.hotel.dto.DtoHotelBrief;
import tech.pinto.catel.hotel.dto.DtoHotelQuery;
import tech.pinto.catel.util.*;
import tech.pinto.catel.vo.hotel.HotelVO;
import tech.pinto.catel.vo.hotel.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HotelService {

    private final MapperHotel mapperHotel;
    private final MapperOrder mapperOrder;
    private final MapperRoom mapperRoom;
    private final MapperRoomConfig mapperRoomConfig;
    private final MapperUsableRoom mapperUsableRoom;
    private final RepoRoomUnit repoRoomUnit;
    private final OssService ossService;
    private final FzyService fzyService;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public HotelService(MapperHotel mapperHotel, MapperRoom mapperRoom, MapperRoomConfig mapperRoomConfig, MapperUsableRoom mapperUsableRoom, OssService ossService, FzyService fzyService, MapperOrder mapperOrder, RepoRoomUnit repoRoomUnit, JPAQueryFactory queryFactory) {
        this.mapperHotel = mapperHotel;
        this.mapperRoom = mapperRoom;
        this.mapperRoomConfig = mapperRoomConfig;
        this.mapperUsableRoom = mapperUsableRoom;
        this.ossService = ossService;
        this.fzyService = fzyService;
        this.mapperOrder = mapperOrder;
        this.repoRoomUnit = repoRoomUnit;
        this.queryFactory = queryFactory;
    }

    public void addHotel(HotelVO hotelVO) {
        Hotel hotel = new Hotel();
        BeanUtil.copyProperties(hotelVO, hotel, CopyOptions.create().setIgnoreNullValue(true));
        mapperHotel.insertHotel(hotel);
    }

    public List<Hotel> getHotelByManager(Integer id) {
        return mapperHotel.selectByManager(id);
    }

    public void updateHotelInfo(HotelVO hotelVO) {
        Hotel hotel = mapperHotel.selectById(hotelVO.getId());
        boolean newPic = null != hotelVO.getPic() && !hotelVO.getPic().equals(hotel.getPic());
        BeanUtil.copyProperties(hotelVO, hotel, CopyOptions.create().setIgnoreNullValue(true));
        if (newPic) {
            hotel.setPic(ossService.savePublic("hotel-res-img-public",
                                               "hotel/" + hotelVO.getId().toString() + "/pic.png", hotelVO.getPic()));
        }
        mapperHotel.updateHotel(hotel);
    }

    public List<Hotel> unboundHotel() {
        return mapperHotel.unboundHotel();
    }

    public List<Hotel> fzySearch(String keyword) {
        List<Hotel> hotels = mapperHotel.selectAllHotel();
        hotels.sort((h1, h2) -> {
            double s1 = fzyService.similarity(keyword, h1);
            double s2 = fzyService.similarity(keyword, h2);
            double delta = s1 - s2;
            return delta > 0 ? 1 : delta < 0 ? -1 : 0;
        });
        return hotels.stream().filter(hotel -> fzyService.similarity(keyword, hotel) > 0.6).collect(Collectors.toList());
    }

    public List<HotelVO> getHot() {
        List<Hotel> all = mapperHotel.selectAllHotel();
        HashMap<Long, Integer> hots = new HashMap<>();
        all.forEach(o -> {
            hots.put(o.getId(), mapperOrder.getHot(o.getId()));
        });
        all.sort(Comparator.comparingInt(x -> hots.get(x.getId())));
        return all.stream().map(o -> {
            HotelVO hotelVO = new HotelVO();
            BeanUtil.copyProperties(o, hotelVO, CopyOptions.create().ignoreNullValue());
            return hotelVO;
        }).collect(Collectors.toList());
    }

    public List<HotelVO> retrieveHotels() {
        return mapperHotel.selectAllHotel().stream().map(po -> {
            HotelVO vo = new HotelVO();
            BeanUtil.copyProperties(po, vo, CopyOptions.create().ignoreNullValue());
            return vo;
        }).collect(Collectors.toList());
    }

    public HotelVO selectById(long hotelId) {
        Hotel hotel = mapperHotel.selectById(hotelId);
        HotelVO hotelVO = new HotelVO();
        BeanUtil.copyProperties(hotel, hotelVO, CopyOptions.create().ignoreNullValue());
        List<Room> rooms = mapperRoom.selectRoomsByHotelId(hotelId);
        List<RoomVO> roomVOS = rooms.stream().map(r -> {
            RoomVO roomVO = new RoomVO();
            BeanUtil.copyProperties(r, roomVO, CopyOptions.create().setIgnoreNullValue(true));
            return roomVO;
        }).collect(Collectors.toList());
        hotelVO.setRooms(roomVOS);
        return hotelVO;
    }

    public List<DtoHotelBrief> hotelQuery(DtoHotelQuery dtoHotelQuery) {
        var inDate = UtilDate.from(dtoHotelQuery.getFilterInDate());
        var outDate = UtilDate.from(dtoHotelQuery.getFilterOutDate());

        var rc = QRoomConfig.roomConfig;
        var h = QHotel.hotel;
        var ru = QRoomUnit.roomUnit;

        var query = queryFactory
            .selectFrom(h)
            .join(rc).on(rc.hotel.eq(h))
            .join(ru).on(ru.roomConfig.eq(rc))
            .where(h.bizRegion.eq(BizRegion.XiDan))
            .where(ru.date.goe(inDate), ru.date.lt(outDate))
            .where(rc.defPrice.gt(100), rc.defPrice.lt(500))
            .groupBy(h, rc)
            .having(ru.count().gt(outDate.toEpochDay() - inDate.toEpochDay()));

        var hotels = query.fetch();

        var foundHotels = hotels.stream().map(AllMapper.X::toBrief).collect(Collectors.toList());

        foundHotels.forEach(System.out::println);
        return foundHotels;

    }

}
