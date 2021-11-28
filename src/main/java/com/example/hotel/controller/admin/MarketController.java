package com.example.hotel.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.example.hotel.bl.user.AccountServiceI;
import com.example.hotel.enums.UserType;
import com.example.hotel.model.User;
import com.example.hotel.util.OopsException;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.user.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/market")
public class MarketController {

    private final AccountServiceI accountService;

    @Autowired
    public MarketController(AccountServiceI accountService) {
        this.accountService = accountService;
    }

    /**
     * create a marketer
     * _in email, phone number and password
     */
    @PostMapping("/new")
    public ResponseVO createMarketer(@RequestBody UserForm userForm) {
        User user = new User();
        user.setUserType(UserType.Marketer);
        BeanUtil.copyProperties(userForm, user, CopyOptions.create().ignoreNullValue());
        try {
            accountService.registerAccount(user);
        } catch (OopsException e) {
            return ResponseVO.buildFailure(e.getMessage());
        }
        return ResponseVO.buildSuccess().setMessage(23);
    }

}
