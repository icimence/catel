package com.example.hotel.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.example.hotel.bl.admin.AdminServiceI;
import com.example.hotel.bl.user.AccountServiceI;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.User;
import com.example.hotel.util.OopsException;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.user.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminServiceI adminService;
    private final AccountServiceI accountService;

    @Autowired
    public AdminController(AdminServiceI adminService, AccountServiceI accountService) {
        this.adminService = adminService;
        this.accountService = accountService;
    }

    /**
     * create hotel manager
     * _in email, phone number and password
     */
    @PostMapping("/new-manager")
    public ResponseVO addManager(@RequestBody UserForm userForm) {
        User user = new User();
        user.setUserType(UserType.HotelManager);
        BeanUtil.copyProperties(userForm, user, CopyOptions.create().ignoreNullValue());
        try {
            accountService.registerAccount(user);
        } catch (OopsException e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(e.getMessage());
        }
        return ResponseVO.buildSuccess().setMessage(1);
    }

    /**
     * remove hotel with given id
     * _in hotel id
     */
    @PostMapping("/rm-hotel")
    public ResponseVO deleteHotel(@RequestParam int id) {
        adminService.deleteHotel(id);
        return ResponseVO.buildSuccess().setMessage(2);
    }

    /**
     * remove user with given id
     * _in user id
     */
    @PostMapping("/rm-user")
    public ResponseVO deleteUser(@RequestParam int id) {
        adminService.deleteUser(id);
        return ResponseVO.buildSuccess().setMessage(3);
    }

    /**
     * bound given hotel to given manager
     * _in hotel id and user id
     */
    @PutMapping("/bind")
    public ResponseVO bindToManager(@RequestParam int hotelId, @RequestParam int managerId) {
        adminService.bindToManager(hotelId, managerId);
        return ResponseVO.buildSuccess().setMessage(managerId == -1 ? 7 : 4);
    }

    /**
     * get all marketers
     * _out all marketers
     */
    @GetMapping("/marketers")
    public ResponseVO getAllMarketers() {
        return ResponseVO.buildSuccess(adminService.getAllMarketers());
    }

    /**
     * get all managers
     * _out all managers
     */
    @GetMapping("/managers")
    public ResponseVO getAllManagers() {
        return ResponseVO.buildSuccess(adminService.getAllManagers());
    }

}
