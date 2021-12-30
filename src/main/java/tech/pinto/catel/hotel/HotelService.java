package tech.pinto.catel.hotel;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import tech.pinto.catel.domain.*;
import tech.pinto.catel.enums.HotelStar;
import tech.pinto.catel.hotel.dto.DtoHotelDetail;
import tech.pinto.catel.room.*;
import tech.pinto.catel.order.MapperOrder;
import tech.pinto.catel.hotel.dto.DtoHotelBrief;
import tech.pinto.catel.hotel.dto.DtoHotelQuery;
import tech.pinto.catel.util.*;
import tech.pinto.catel.vo.hotel.HotelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HotelService {

    private final MapperHotel mapperHotel;
    private final MapperOrder mapperOrder;
    private final MapperRoom mapperRoom;
    private final RepoRoomUnit repoRoomUnit;
    private final RepoHotel repoHotel;
    private final RepoRoom repoRoom;
    private final OssService ossService;
    private final FzyService fzyService;
    private final JPAQueryFactory queryFactory;
    private final MapX mapX;

    @Autowired
    public HotelService(MapperHotel mapperHotel, MapperRoom mapperRoom, RepoHotel repoHotel, RepoRoom repoRoom, OssService ossService, FzyService fzyService, MapperOrder mapperOrder, RepoRoomUnit repoRoomUnit, JPAQueryFactory queryFactory, MapX mapX) {
        this.mapperHotel = mapperHotel;
        this.mapperRoom = mapperRoom;
        this.repoHotel = repoHotel;
        this.repoRoom = repoRoom;
        this.ossService = ossService;
        this.fzyService = fzyService;
        this.mapperOrder = mapperOrder;
        this.repoRoomUnit = repoRoomUnit;
        this.queryFactory = queryFactory;
        this.mapX = mapX;
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
        // TODO update hotel info
        var hotel = repoHotel.getById(0L);
//        boolean newPic = null != hotelVO.getPic() && !hotelVO.getPic().equals(hotel.getPic());
//        BeanUtil.copyProperties(hotelVO, hotel, CopyOptions.create().setIgnoreNullValue(true));
//        if (newPic) {
//            hotel.setPic(ossService.savePublic("hotel-res-img-public",
//                                               "hotel/" + hotelVO.getId().toString() + "/pic.png", hotelVO.getPic()));
//        }
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

    public List<DtoHotelBrief> getHot(int limit) {
        var h = QHotel.hotel;
        var cs = QCommentStat.commentStat;

        var hottest = queryFactory
            .selectFrom(h)
            .join(cs)
            .on(h.id.eq(cs.id))
            .orderBy(cs.rate.desc())
            .limit(limit)
            .fetch();

        return hottest.stream().map(mapX::toBrief).collect(Collectors.toList());
    }

    public List<HotelVO> retrieveHotels() {
        return mapperHotel.selectAllHotel().stream().map(po -> {
            HotelVO vo = new HotelVO();
            BeanUtil.copyProperties(po, vo, CopyOptions.create().ignoreNullValue());
            return vo;
        }).collect(Collectors.toList());
    }

    public DtoHotelDetail getDetail(long hotelId) {
        var hotel = repoHotel.getById(hotelId);
        var detail = mapX.toDetail(hotel);
        var configs = hotel.getConfigs();
        var configInfos = configs.stream().map(mapX::toInfo).collect(Collectors.toList());
        detail.setRooms(configInfos);
        return detail;
    }

    public DtoHotelDetail luckyOne() {
        var hotels = repoHotel.findAll();
        var id = UtilRandom.ofInt(0, hotels.size());
        return mapX.toDetail(hotels.get(id));
    }

    public List<DtoHotelBrief> hotelQuery(DtoHotelQuery dtoHotelQuery) throws OopsException {
        var queryParam = mapX.toQueryParam(dtoHotelQuery);
        var filter = queryParam.getFilter();
        System.out.println(queryParam);

        var inDate = filter.getInDate();
        var outDate = filter.getOutDate();

        if ((inDate == null) != (outDate == null)) {
            throw new OopsException(9);
        }

        var rc = QRoomConfig.roomConfig;
        var h = QHotel.hotel;
        var ru = QRoomUnit.roomUnit;
        var cs = QCommentStat.commentStat;

        var query = queryFactory
            .select(h, cs.rate)
            .from(h)
            .distinct()
            .join(cs).on(cs.hotel.eq(h))
            .join(rc).on(rc.hotel.eq(h))
            .join(ru).on(ru.id.roomConfig.eq(rc));

        if (filter.getRegion() != null) {
            query = query.where(h.bizRegion.eq(filter.getRegion()));
        }

        if (filter.getRate() != null) {
            query = query.where(cs.rate.goe(filter.getRate()));
        }

        if (filter.getPriceLower() != null) {
            query = query.where(rc.defPrice.gt(filter.getPriceLower()));
        }

        if (filter.getPriceUpper() != null) {
            query = query.where(rc.defPrice.lt(filter.getPriceUpper()));
        }

        if (filter.getStars() != null) {
            var pred = h.id.ne(h.id);
            for (var s : filter.getStars()) {
                var hs = HotelStar.from(s);
                if (hs != null) pred = pred.or(
                    h.hotelStar.eq(hs)
                );
            }
            query = query.where(pred);
        }

        if (inDate != null) {
            var duration = outDate.toEpochDay() - inDate.toEpochDay();
            query = query
                .where(ru.id.date.goe(inDate), ru.id.date.lt(outDate), ru.number.ne(0))
                .groupBy(h, rc)
                .having(ru.count().eq(duration));
        }

        if (queryParam.getLimit() != null) {
            var page = queryParam.getPage() == null ? 0L : queryParam.getPage();
            query = query
                .offset(page * queryParam.getLimit())
                .limit(queryParam.getLimit());
        }

        query = query.orderBy(cs.rate.desc());

        return query
            .fetch().stream()
            .map(t -> {
                var hotel = t.get(h);
                var rate = Objects.requireNonNull(t.get(cs.rate));
                var dto = mapX.toBrief(hotel);
                dto.setRate(rate);
                return dto;
            })
            .collect(Collectors.toList());

    }
}
