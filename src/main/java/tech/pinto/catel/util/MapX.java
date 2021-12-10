package tech.pinto.catel.util;

import org.hibernate.SessionFactory;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import tech.pinto.catel.comment.dto.DtoPublishComment;
import tech.pinto.catel.domain.Hotel;
import tech.pinto.catel.hotel.QueryParam;
import tech.pinto.catel.hotel.RepoHotel;
import tech.pinto.catel.hotel.dto.DtoHotelBrief;
import tech.pinto.catel.hotel.dto.DtoHotelQuery;
import tech.pinto.catel.domain.Comment;
import tech.pinto.catel.domain.Order;
import tech.pinto.catel.order.RepoOrder;
import tech.pinto.catel.order.dto.DtoOrderBrief;
import tech.pinto.catel.order.dto.DtoReservePersonal;
import tech.pinto.catel.domain.CreditEntry;
import tech.pinto.catel.user.RepoUser;
import tech.pinto.catel.domain.Resident;
import tech.pinto.catel.order.dto.DtoOrderDetail;
import tech.pinto.catel.order.dto.DtoReserve;
import tech.pinto.catel.domain.RoomConfig;
import tech.pinto.catel.room.dto.DtoRoomInfo;
import tech.pinto.catel.domain.User;
import tech.pinto.catel.user.dto.DtoResidentAddition;
import tech.pinto.catel.user.dto.DtoUserInfo;

import javax.persistence.EntityManagerFactory;

@Mapper(componentModel = "spring")
@DecoratedWith(MapEx.class)
public abstract class MapX {
    @Autowired
    protected RepoHotel repoHotel;
    @Autowired
    protected RepoUser repoUser;
    @Autowired
    protected RepoOrder repoOrder;
    @Autowired
    protected EntityManagerFactory entityManagerFactory;

    @Mapping(target = "birthday", source = "birthday", dateFormat = "MM/dd/yyyy")
    public abstract Resident toPerson(DtoResidentAddition src);

    @Mapping(target = "checkInDate", source = "checkInDate", dateFormat = "MM/dd/yyyy")
    @Mapping(target = "checkOutDate", source = "checkOutDate", dateFormat = "MM/dd/yyyy")
    @Mapping(target = "hotel", source = "hotelId")
    @Mapping(target = "user", source = "userId")
    public abstract Order toOrder(DtoReserve src);

    public abstract Order toPersonOrder(DtoReservePersonal src);

    public abstract Comment toComment(DtoPublishComment src);

    @Mapping(target = "checkInDate", source = "checkInDate", dateFormat = "MM/dd/yyyy")
    @Mapping(target = "checkOutDate", source = "checkOutDate", dateFormat = "MM/dd/yyyy")
    @Mapping(target = "createdTime", source = "createdTime", dateFormat = "MM/dd/yyyy HH:mm:ss")
    public abstract DtoOrderDetail toDetail(Order src);

    @Mapping(target = "headPic", source = "pic")
    public abstract DtoHotelBrief toBrief(Hotel src);

    @Mapping(target = "price", source = "defPrice")
    public abstract DtoRoomInfo toRoomInfo(RoomConfig src);

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

    public Hotel idToHotel(long id) {
        return repoHotel.getById(id);
    }

    public User idToUser(long id) {
        return repoUser.getById(id);
    }

    public Order idToOrder(long id) {return repoOrder.getById(id);}
    
    public abstract DtoOrderBrief toBrief(Order order);
    
    public abstract DtoUserInfo toInfo(User src);
}

