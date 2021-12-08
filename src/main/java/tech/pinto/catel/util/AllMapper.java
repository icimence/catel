package tech.pinto.catel.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tech.pinto.catel.comment.dto.DtoPublishComment;
import tech.pinto.catel.hotel.Hotel;
import tech.pinto.catel.hotel.dto.DtoHotelBrief;
import tech.pinto.catel.model.Comment;
import tech.pinto.catel.model.Order;
import tech.pinto.catel.model.Person;
import tech.pinto.catel.order.dto.DtoOrderDetail;
import tech.pinto.catel.order.dto.DtoReserve;
import tech.pinto.catel.room.RoomConfig;
import tech.pinto.catel.room.dto.DtoRoomInfo;
import tech.pinto.catel.user.dto.DtoResidentAddition;

@Mapper
public interface AllMapper {
    AllMapper X = Mappers.getMapper(AllMapper.class);

    @Mapping(target = "birthday", source = "birthday", dateFormat = "MM/dd/yyyy")
    Person toPerson(DtoResidentAddition src);

    @Mapping(target = "checkInDate", source = "checkInDate", dateFormat = "MM/dd/yyyy")
    @Mapping(target = "checkOutDate", source = "checkOutDate", dateFormat = "MM/dd/yyyy")
    Order toOrder(DtoReserve src);

    Comment toComment(DtoPublishComment src);

    @Mapping(target = "checkInDate", source = "checkInDate", dateFormat = "MM/dd/yyyy")
    @Mapping(target = "checkOutDate", source = "checkOutDate", dateFormat = "MM/dd/yyyy")
    @Mapping(target = "createdTime", source = "createdTime", dateFormat = "MM/dd/yyyy HH:mm:ss")
    DtoOrderDetail toDetail(Order src);

    @Mapping(target = "headPic", source = "pic")
    DtoHotelBrief toBrief(Hotel src);

    @Mapping(target = "price", source = "defPrice")
    DtoRoomInfo toRoomInfo(RoomConfig src);
}

