package com.example.hotel.util;

import com.example.hotel.dto.CreateResidentDTO;
import com.example.hotel.po.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AllMapper {
    AllMapper X = Mappers.getMapper(AllMapper.class);

    @Mapping(target = "birthday", source = "birthday", dateFormat = "MM/dd/yyyy")
    Person toPerson(CreateResidentDTO src);
}
