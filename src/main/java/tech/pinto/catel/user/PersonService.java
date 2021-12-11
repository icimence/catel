package tech.pinto.catel.user;

import tech.pinto.catel.domain.Resident;
import tech.pinto.catel.user.dto.DtoResidentDeletion;
import tech.pinto.catel.user.dto.DtoResidentAddition;
import tech.pinto.catel.util.MapX;
import tech.pinto.catel.util.OopsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonMapper personMapper;
    private final RepoResident repoResident;
    private final MapX mapX;

    @Autowired
    public PersonService(PersonMapper personMapper, RepoResident repoResident, MapX mapX) {
        this.personMapper = personMapper;
        this.repoResident = repoResident;
        this.mapX = mapX;
    }

    public void addResident(DtoResidentAddition dtoResidentAddition) {
        Resident resident = mapX.toPerson(dtoResidentAddition);
        System.out.println(resident.toString());
        personMapper.insert(resident);
    }

    public void delete(DtoResidentDeletion dtoDeleteResident) {
        personMapper.deleteById(dtoDeleteResident.getResidentId());
    }

    public void update(Resident resident) {
        repoResident.save(resident);
    }

    public List<Resident> selectByUserId(long userId) {
        return repoResident.findByOwnerId(userId);
    }

    public Resident select(int id) throws OopsException {
        Resident resident = personMapper.select(id);
        if (null == resident) throw new OopsException(5);
        return resident;
    }

}
