package tech.pinto.catel.user;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import tech.pinto.catel.user.dto.DtoResidentDeletion;
import tech.pinto.catel.user.dto.DtoResidentAddition;
import tech.pinto.catel.model.Person;
import tech.pinto.catel.util.OopsException;
import tech.pinto.catel.util.Response;
import tech.pinto.catel.vo.user.PersonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * create a person
     * _in basic person info. phone number, birthday, id no., real name
     */
    @PostMapping("/")
    public Response addResident(@RequestBody DtoResidentAddition createResidentVO) {
//        Person person = new Person();
//        BeanUtil.copyProperties(createResidentVO, person, CopyOptions.create().ignoreNullValue());
        personService.addResident(createResidentVO);
        return Response.buildSuccess().setMessage(19);
    }

    /**
     * delete person by id
     * _in person id
     */
    @DeleteMapping("/")
    public Response delete(@RequestBody DtoResidentDeletion dtoDeleteResident) {
        personService.delete(dtoDeleteResident);
        return Response.buildSuccess().setMessage(20);
    }

    /**
     * update person info
     * _in totally same to creation
     */
    @PutMapping("/")
    public Response update(@RequestBody PersonVO personVO) {
        Person person = new Person();
        BeanUtil.copyProperties(personVO, person, CopyOptions.create().ignoreNullValue());
        personService.update(person);
        return Response.buildSuccess().setMessage(21);
    }

    /**
     * get all person info created by given user
     * _in user id
     */
    @GetMapping("/by-user")
    public Response getByUserId(@RequestParam int id) {
        List<PersonVO> res = personService.selectByUserId(id).stream().map((e) -> {
            PersonVO personVO = new PersonVO();
            BeanUtil.copyProperties(e, personVO);
            return personVO;
        }).collect(Collectors.toList());
        return Response.buildSuccess(res);
    }

    /**
     * get person by id
     * _in person id
     */
    @GetMapping("/")
    public Response select(int id) {
        try {
            Person person = personService.select(id);
            return Response.buildSuccess(person);
        } catch (OopsException e) {
            e.printStackTrace();
            return Response.buildFailure(e.getMessage());
        }
    }

}
