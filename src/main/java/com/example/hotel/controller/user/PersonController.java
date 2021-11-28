package com.example.hotel.controller.user;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.example.hotel.blImpl.user.PersonService;
import com.example.hotel.dto.DtoDeleteResident;
import com.example.hotel.dto.DtoResidentAddition;
import com.example.hotel.model.Person;
import com.example.hotel.util.OopsException;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.user.PersonVO;
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
    public ResponseVO addResident(@RequestBody DtoResidentAddition createResidentVO) {
//        Person person = new Person();
//        BeanUtil.copyProperties(createResidentVO, person, CopyOptions.create().ignoreNullValue());
        personService.addResident(createResidentVO);
        return ResponseVO.buildSuccess().setMessage(19);
    }

    /**
     * delete person by id
     * _in person id
     */
    @DeleteMapping("/")
    public ResponseVO delete(@RequestBody DtoDeleteResident dtoDeleteResident) {
        personService.delete(dtoDeleteResident);
        return ResponseVO.buildSuccess().setMessage(20);
    }

    /**
     * update person info
     * _in totally same to creation
     */
    @PutMapping("/")
    public ResponseVO update(@RequestBody PersonVO personVO) {
        Person person = new Person();
        BeanUtil.copyProperties(personVO, person, CopyOptions.create().ignoreNullValue());
        personService.update(person);
        return ResponseVO.buildSuccess().setMessage(21);
    }

    /**
     * get all person info created by given user
     * _in user id
     */
    @GetMapping("/by-user")
    public ResponseVO getByUserId(@RequestParam int id) {
        List<PersonVO> res = personService.selectByUserId(id).stream().map((e) -> {
            PersonVO personVO = new PersonVO();
            BeanUtil.copyProperties(e, personVO);
            return personVO;
        }).collect(Collectors.toList());
        return ResponseVO.buildSuccess(res);
    }

    /**
     * get person by id
     * _in person id
     */
    @GetMapping("/")
    public ResponseVO select(int id) {
        try {
            Person person = personService.select(id);
            return ResponseVO.buildSuccess(person);
        } catch (OopsException e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

}
