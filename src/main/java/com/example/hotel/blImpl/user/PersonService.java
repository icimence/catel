package com.example.hotel.blImpl.user;

import com.example.hotel.bl.user.PersonServiceI;
import com.example.hotel.data.user.PersonMapper;
import com.example.hotel.dto.DtoDeleteResident;
import com.example.hotel.model.Person;
import com.example.hotel.util.AllMapper;
import com.example.hotel.util.OopsException;
import com.example.hotel.dto.DtoResidentAddition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonMapper personMapper;

    @Autowired
    public PersonService(PersonMapper personMapper) {this.personMapper = personMapper;}

    public void addResident(DtoResidentAddition dtoResidentAddition) {
        Person person = AllMapper.X.toPerson(dtoResidentAddition);
        System.out.println(person.toString());
        personMapper.insert(person);
    }

    public void delete(DtoDeleteResident dtoDeleteResident) {
        personMapper.deleteById(dtoDeleteResident.getResidentId());
    }

    public void update(Person person) {
        personMapper.update(person);
    }

    public List<Person> selectByUserId(int userId) {
        return personMapper.selectByUserId(userId);
    }

    public Person select(int id) throws OopsException {
        Person person = personMapper.select(id);
        if (null == person) throw new OopsException(5);
        return person;
    }

}
