package com.example.hotel.controller.user;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.example.hotel.bl.user.PersonServiceI;
import com.example.hotel.po.Person;
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

    private final PersonServiceI personServiceI;

    @Autowired
    public PersonController(PersonServiceI personServiceI) {
        this.personServiceI = personServiceI;
    }

    /**
     * create a person
     * _in basic person info. phone number, birthday, id no., real name
     */
    @PostMapping("/new")
    public ResponseVO insert(@RequestBody PersonVO personVO) {
        Person person = new Person();
        BeanUtil.copyProperties(personVO, person, CopyOptions.create().ignoreNullValue());
        personServiceI.insert(person);
        return ResponseVO.buildSuccess().setMessage(19);
    }

    /**
     * delete person by id
     * _in person id
     */
    @PostMapping("/rm")
    public ResponseVO delete(@RequestParam int id) {
        personServiceI.delete(id);
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
        personServiceI.update(person);
        return ResponseVO.buildSuccess().setMessage(21);
    }

    /**
     * get all person info created by given user
     * _in user id
     */
    @GetMapping("/by-user")
    public ResponseVO getByUserId(@RequestParam int id) {
        List<PersonVO> res = personServiceI.selectByUserId(id).stream().map((e) -> {
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
            Person person = personServiceI.select(id);
            return ResponseVO.buildSuccess(person);
        } catch (OopsException e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

}
