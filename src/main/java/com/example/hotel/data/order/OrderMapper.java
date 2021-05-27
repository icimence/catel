package com.example.hotel.data.order;

import com.example.hotel.po.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Insert(value = "insert into hotel.OrderList(user_id, person_id, hotel_id, check_in_date, check_out_date, room_type," +
            " room_num, people_num, have_child, create_date, price, order_state,breakfast,comment_id,credit_delta,hotel_name) values " +
            " (#{order.userId} , #{order.personId}  ,#{order.hotelId} ,#{order.checkInDate} ,#{order.checkOutDate} ," +
            " #{order.roomType} ,#{order.roomNum} ,#{order.peopleNum} ,#{order.haveChild} ,#{order.createDate} ," +
            " #{order.price} ,#{order.orderState},#{order.breakfast},#{order.commentId} ,#{order.creditDelta},#{order.hotelName})")
    void addOrder(@Param("order") Order order);

    @Select(value = "select * from hotel.OrderList")
    List<Order> getAllOrders();

    @Select(value = "select * from hotel.OrderList where user_id=#{userId} ")
    List<Order> getUserOrders(@Param("userId") int userId);

    @Update(value = "update hotel.OrderList set order_state='Canceled' where id=#{orderId} ")
    void annulOrder(@Param("orderId") int orderId);

    @Update(value = "update hotel.OrderList set order_state=#{order.orderState} ,credit_delta=#{order.creditDelta} , comment_id=#{order.commentId} where id=#{order.id} ")
    void update(@Param("order") Order order);

    @Update(value = "update hotel.OrderList set order_state='Finished',credit_delta=price where check_out_date <= curdate() and order_state='Available'")
    void finish();

    @Update(value = "update hotel.Hotel h set rate=coalesce(" +
            "(select x.r from (select hotel_id,avg(score) r from hotel.Comment group by hotel_id) x " +
            "where hotel_id=h.id),5) where true")
    void rate();

    @Update(value = "update hotel.OrderList set order_state='Expired',credit_delta=0 where id=#{id} ")
    void expire(@Param("id") int id);

    @Select(value = "select * from hotel.OrderList where id=#{orderId} ")
    Order getOrderById(@Param("orderId") int orderId);

    @Select(value = "select * from hotel.OrderList where hotel_id=#{hotelId} ")
    List<Order> getOrderByHotel(@Param("hotelId") Integer hotelId);

    @Select(value = "select * from hotel.OrderList where order_state='Available' and user_id=#{id} ")
    List<Order> getAvailable(@Param("id") int id);

    @Select(value = "select count(*) from hotel.OrderList where hotel_id=#{id} ")
    int getHot(@Param("id") int id);

}
