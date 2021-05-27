package com.example.hotel.bl.admin;

import com.example.hotel.po.User;

import java.util.List;

public interface AdminServiceI {

    /**
     * 获得所有酒店管理人员信息
     */
    List<User> getAllManagers();

    /**
     * 将酒店绑定至管理员
     */
    void bindToManager(int hotelId, int managerId);

    /**
     * 删除酒店
     */
    void deleteHotel(int id);

    /**
     * 删除用户
     */
    void deleteUser(int id);

    List<User> getAllMarketers();

}
