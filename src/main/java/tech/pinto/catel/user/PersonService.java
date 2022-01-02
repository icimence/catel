package tech.pinto.catel.user;

import tech.pinto.catel.domain.Resident;
import tech.pinto.catel.user.dto.DtoResidentDeletion;
import tech.pinto.catel.user.dto.DtoResidentAddition;
import tech.pinto.catel.user.dto.DtoResidentUpdate;
import tech.pinto.catel.util.MapX;
import tech.pinto.catel.util.error.OopsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final RepoResident repoResident;
    private final MapX mapX;

    @Autowired
    public PersonService(RepoResident repoResident, MapX mapX) {
        this.repoResident = repoResident;
        this.mapX = mapX;
    }

    public void addResident(DtoResidentAddition dtoResidentAddition) {
        Resident resident = mapX.toPerson(dtoResidentAddition);
        repoResident.save(resident);
    }

    public void delete(DtoResidentDeletion dtoDeleteResident) {
        repoResident.deleteById(dtoDeleteResident.getResidentId());
    }

    public void update(DtoResidentUpdate dtoResidentUpdate) {
        var resident = repoResident.getById(dtoResidentUpdate.getResidentId());
        resident.setRealName(dtoResidentUpdate.getName());
        resident.setPhoneNumber(dtoResidentUpdate.getPhoneNumber());
        repoResident.save(resident);
    }

    public List<Resident> selectByUserId(long userId) {
        return repoResident.findByOwnerId(userId);
    }

    public Resident select(long id) throws OopsException {
        var resident = repoResident.findById(id);
        if (resident.isEmpty()) throw new OopsException(5);
        return resident.get();
    }

}
