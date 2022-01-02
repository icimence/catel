package tech.pinto.catel.util;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import tech.pinto.catel.comment.dto.DtoComment;
import tech.pinto.catel.comment.dto.DtoPublishComment;
import tech.pinto.catel.coupon.dto.DtoCouponRelated;
import tech.pinto.catel.coupon.dto.DtoUsableCoupon;
import tech.pinto.catel.domain.*;
import tech.pinto.catel.hotel.QueryParam;
import tech.pinto.catel.hotel.RepoHotel;
import tech.pinto.catel.hotel.dto.DtoHotelBrief;
import tech.pinto.catel.hotel.dto.DtoHotelDetail;
import tech.pinto.catel.hotel.dto.DtoHotelQuery;
import tech.pinto.catel.order.RepoOrder;
import tech.pinto.catel.order.dto.DtoOrderBrief;
import tech.pinto.catel.order.dto.DtoReservePersonal;
import tech.pinto.catel.room.dto.DtoRoomEntry;
import tech.pinto.catel.room.dto.DtoConfigInfo;
import tech.pinto.catel.user.RepoUser;
import tech.pinto.catel.order.dto.DtoOrderDetail;
import tech.pinto.catel.order.dto.DtoReserve;
import tech.pinto.catel.user.dto.DtoRegister;
import tech.pinto.catel.user.dto.DtoResidentAddition;
import tech.pinto.catel.user.dto.DtoUserInfo;

@Mapper(componentModel = "spring")
@DecoratedWith(MapEx.class)
public abstract class MapX {
    @Autowired
    protected RepoHotel repoHotel;
    @Autowired
    protected RepoUser repoUser;
    @Autowired
    protected RepoOrder repoOrder;

    @Mapping(target = "birthday", source = "birthday", dateFormat = "MM/dd/yyyy")
    @Mapping(target = "owner", source = "userId")
    @Mapping(target = "id", ignore = true)
    public abstract Resident toPerson(DtoResidentAddition src);

    @Mapping(target = "checkInDate", source = "checkInDate", dateFormat = "MM/dd/yyyy")
    @Mapping(target = "checkOutDate", source = "checkOutDate", dateFormat = "MM/dd/yyyy")
    @Mapping(target = "hotel", source = "hotelId")
    @Mapping(target = "user", source = "userId")
    @Mapping(target = "id", ignore = true)
    public abstract Order toOrder(DtoReserve src);

    @Mapping(target = "id", ignore = true)
    public abstract User toUser(DtoRegister dtoRegister);

    @Mapping(target = "id", ignore = true)
    public abstract Order toPersonOrder(DtoReservePersonal src);

    @Mapping(target = "order", source = "orderId")
    @Mapping(target = "id", ignore = true)
    public abstract Comment toComment(DtoPublishComment src);

    @Mapping(target = "number", source = "roomNumber")
    @Mapping(target = "price", source = "defPrice")
    @Mapping(target = "id.roomConfig", source = ".")
    public abstract RoomUnit toUnit(RoomConfig config);

    @Mapping(target = "filter.priceLower", source = "filterPriceLower")
    @Mapping(target = "filter.priceUpper", source = "filterPriceUpper")
    @Mapping(target = "filter.rate", source = "filterRate")
    @Mapping(target = "filter.region", source = "filterLocation")
    @Mapping(target = "filter.inDate", source = "filterInDate", dateFormat = "MM/dd/yyyy")
    @Mapping(target = "filter.outDate", source = "filterOutDate", dateFormat = "MM/dd/yyyy")
    public abstract QueryParam toQueryParam(DtoHotelQuery src);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "delta", source = "price")
    @Mapping(target = "order", source = "id")
    public abstract CreditEntry toEntry(Order order);

    @Mapping(target = "checkInDate", source = "checkInDate", dateFormat = "MM/dd/yyyy")
    @Mapping(target = "checkOutDate", source = "checkOutDate", dateFormat = "MM/dd/yyyy")
    @Mapping(target = "createdTime", source = "createdTime", dateFormat = "MM/dd/yyyy HH:mm:ss")
    public abstract DtoOrderDetail toDetail(Order src);

    public abstract DtoHotelDetail toDetail(Hotel hotel);

    @Mapping(target = "star", source = "hotelStar")
    public abstract DtoHotelBrief toBrief(Hotel src);

    @Mapping(target = "price", source = "defPrice")
    public abstract DtoRoomEntry toRoomInfo(RoomConfig src);

    public abstract DtoOrderBrief toBrief(Order order);

    public abstract DtoUserInfo toInfo(User src);

    @Mapping(target = "roomType", source = "type")
    @Mapping(target = "price", source = "defPrice")
    @Mapping(target = "total", source = "roomNumber")
    public abstract DtoConfigInfo toInfo(RoomConfig src);

    @Mapping(target = "userId", source = "user.id")
    public abstract DtoComment toDtoComment(Comment src);

    @Mapping(target = "amount", source = "couponBase.discountAmount")
    @Mapping(target = "outdatedWhen", source = "couponBase.timeOutdated", dateFormat = "MM/dd/yyyy")
    public abstract DtoUsableCoupon toUsableCoupon(CouponBase couponBase, DtoCouponRelated related);

    public Hotel idToHotel(long id) {
        return repoHotel.getById(id);
    }

    public User idToUser(long id) {
        return repoUser.getById(id);
    }

    public Order idToOrder(long id) {
        return repoOrder.getById(id);
    }

}

