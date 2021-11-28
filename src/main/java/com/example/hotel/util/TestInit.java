package com.example.hotel.util;

import com.example.hotel.controller.order.CommentController;
import com.example.hotel.controller.admin.MarketController;
import com.example.hotel.controller.admin.AdminController;
import com.example.hotel.controller.coupon.CouponController;
import com.example.hotel.controller.hotel.HotelController;
import com.example.hotel.controller.hotel.RoomController;
import com.example.hotel.controller.order.OrderController;
import com.example.hotel.controller.user.AccountController;
import com.example.hotel.controller.user.PersonController;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.enums.RoomType;
import com.example.hotel.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestInit {

    private final AccountController accountController;
    private final PersonController personController;
    private final CommentController commentController;
    private final MarketController marketController;
    private final OrderController orderController;
    private final HotelController hotelController;
    private final RoomController roomController;
    private final CouponController couponController;
    private final AdminController adminController;
    private final RoomMapper roomMapper;

    @Autowired
    public TestInit(AccountController accountController,
                    PersonController personController,
                    CommentController commentController,
                    MarketController marketController,
                    OrderController orderController,
                    HotelController hotelController,
                    RoomController roomController,
                    CouponController couponController,
                    AdminController adminController,
                    RoomMapper roomMapper) {
        this.accountController = accountController;
        this.personController = personController;
        this.commentController = commentController;
        this.marketController = marketController;
        this.orderController = orderController;
        this.hotelController = hotelController;
        this.roomController = roomController;
        this.couponController = couponController;
        this.adminController = adminController;
        this.roomMapper = roomMapper;
    }

    public void testInit() {
        if (roomMapper.ok()) return;
        roomController.addRoomInfo(new Room(1, 3, 1, 199, true, RoomType.BigBed));
        roomController.addRoomInfo(new Room(1, 3, 20, 299, true, RoomType.DoubleBed));
        roomController.addRoomInfo(new Room(2, 3, 20, 199, true, RoomType.BigBed));
        roomController.addRoomInfo(new Room(2, 3, 20, 499, true, RoomType.DoubleBed));
        roomController.addRoomInfo(new Room(3, 3, 20, 699, true, RoomType.Family));
        roomController.addRoomInfo(new Room(3, 3, 20, 599, false, RoomType.Family));
    }

}
