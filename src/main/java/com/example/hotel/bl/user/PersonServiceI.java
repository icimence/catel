package com.example.hotel.bl.user;

import com.example.hotel.po.Person;
import com.example.hotel.util.OopsException;
import com.example.hotel.dto.CreateResidentDTO;

import java.util.List;

public interface PersonServiceI {

    void addResident(CreateResidentDTO createResidentVO);

    void delete(int id);

    void update(Person person);

    List<Person> selectByUserId(int userId);

    Person select(int id) throws OopsException;

}
