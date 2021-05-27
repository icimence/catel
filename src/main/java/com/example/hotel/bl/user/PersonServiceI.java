package com.example.hotel.bl.user;

import com.example.hotel.po.Person;
import com.example.hotel.util.OopsException;

import java.util.List;

public interface PersonServiceI {

    void insert(Person person);

    void delete(int id);

    void update(Person person);

    List<Person> selectByUserId(int userId);

    Person select(int id) throws OopsException;

}
