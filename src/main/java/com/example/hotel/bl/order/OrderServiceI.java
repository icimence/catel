package com.example.hotel.bl.order;

import com.example.hotel.po.Order;
import com.example.hotel.util.OopsException;
import com.example.hotel.vo.CreditTransVO;
import com.example.hotel.vo.OrderVO;

import java.util.List;

public interface OrderServiceI {

    /**
     * 预订酒店
     */
    void addOrder(OrderVO orderVO) throws OopsException;

    /**
     * 获得所有订单信息
     */
    List<OrderVO> getAllOrders();

    /**
     * 获得指定用户的所有订单信息
     */
    List<OrderVO> getUserOrders(int userId);

    /**
     * 撤销订单
     */
    void annulOrder(int orderId);

    /**
     * 查看酒店的所有订单
     */
    List<Order> getHotelOrders(Integer hotelId);

    List<Order> getOrderByManager(Integer managerId);

    List<CreditTransVO> getCreditTransaction(int userId);

    void expire(int orderId);

    List<Order> available(int id);

}
