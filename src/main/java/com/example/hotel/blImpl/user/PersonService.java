package com.example.hotel.blImpl.user;

import com.example.hotel.bl.user.PersonServiceI;
import com.example.hotel.data.user.PersonMapper;
import com.example.hotel.po.Person;
import com.example.hotel.util.OopsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService implements PersonServiceI {

    private final PersonMapper personMapper;

    @Autowired
    public PersonService(PersonMapper personMapper) {this.personMapper = personMapper;}

    @Override
    public void insert(Person person) {
        personMapper.insert(person);
    }

    @Override
    public void delete(int id) {
        personMapper.delete(id);
    }

    @Override
    public void update(Person person) {
        personMapper.update(person);
    }

    @Override
    public List<Person> selectByUserId(int userId) {
        return personMapper.selectByUserId(userId);
    }

    @Override
    public Person select(int id) throws OopsException {
        Person person = personMapper.select(id);
        if (null == person) throw new OopsException(5);
        return person;
    }

}
