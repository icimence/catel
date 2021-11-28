package com.example.hotel.bl.user;

import com.example.hotel.model.Order;
import com.example.hotel.model.User;
import com.example.hotel.util.OopsException;
import com.example.hotel.vo.order.CreditUpVO;
import com.example.hotel.vo.user.UserForm;
import com.example.hotel.vo.user.UserInfo;
import com.example.hotel.vo.user.VipForm;

public interface AccountServiceI {

    /**
     * 注册账号
     */
    void registerAccount(User user) throws OopsException;

    /**
     * 用户登录，登录成功会将用户信息保存再session中
     */
    User login(UserForm userForm) throws OopsException;

    UserInfo getUserInfo(int id) throws OopsException;

    User getUserById(int id) throws OopsException;

    /**
     * 更新用户个人信息
     */
    void updateInfo(UserInfo userInfo) throws OopsException;

    void creditDown(Order order);

    void vip(VipForm vipForm);

    void creditUp(CreditUpVO creditUpVO) throws OopsException;

}
