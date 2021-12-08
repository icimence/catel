package tech.pinto.catel.user;

import tech.pinto.catel.user.dto.DtoResidentDeletion;
import tech.pinto.catel.user.dto.DtoResidentAddition;
import tech.pinto.catel.model.Person;
import tech.pinto.catel.util.AllMapper;
import tech.pinto.catel.util.OopsException;
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

    public void delete(DtoResidentDeletion dtoDeleteResident) {
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
