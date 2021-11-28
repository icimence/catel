package com.example.hotel.util;

import com.example.hotel.dto.DtoOrderDetail;
import com.example.hotel.dto.DtoPublishComment;
import com.example.hotel.dto.DtoReserve;
import com.example.hotel.dto.DtoResidentAddition;
import com.example.hotel.model.Comment;
import com.example.hotel.model.Order;
import com.example.hotel.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

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
}
