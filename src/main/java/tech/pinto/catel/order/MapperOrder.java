package tech.pinto.catel.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import tech.pinto.catel.domain.Order;

import java.util.List;

@Mapper
public interface MapperOrder extends BaseMapper<Order> {

    @Select(value = "select * from hotel.OrderList")
    List<Order> getAllOrders();

    @Select(value = "select * from hotel.OrderList where user_id=#{userId} ")
    List<Order> getUserOrders(@Param("userId") int userId);

    @Update(value = "update hotel.Hotel h set rate=coalesce(" +
            "(select x.r from (select hotel_id,avg(score) r from hotel.Comment group by hotel_id) x " +
            "where hotel_id=h.id),5) where true")
    void rate();

    @Update(value = "update hotel.OrderList set order_state='Expired',credit_delta=0 where id=#{id} ")
    void expire(@Param("id") int id);

    @Select(value = "select * from hotel.OrderList where hotel_id=#{hotelId} ")
    List<Order> getOrderByHotel(@Param("hotelId") long hotelId);

    @Select(value = "select * from hotel.OrderList where order_state='Available' and user_id=#{id} ")
    List<Order> getAvailable(@Param("id") int id);

    @Select(value = "select count(*) from hotel.OrderList where hotel_id=#{id} ")
    int getHot(@Param("id") long id);

}
