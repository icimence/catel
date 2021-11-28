package com.example.hotel.data.admin;

import com.example.hotel.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {

    @Delete(value = "delete from hotel.Hotel where id=#{id}")
    void deleteHotel(@Param("id") int id);

    @Delete(value = "delete from hotel.User where id=#{id}")
    void deleteUser(@Param("id") int id);

    @Update(value = "update hotel.Hotel set manager_id=#{managerId} where id=#{hotelId} ")
    void bind(@Param("hotelId") int hotelId, @Param("managerId") int managerId);

    @Update(value = "update hotel.Hotel set manager_id=-1 where manager_id=#{id}")
    void beforeDelete(@Param("id") int id);

    @Select(value = "select * from hotel.User where user_type='HotelManager'")
    List<User> getAllManagers();

    @Select(value = "select * from hotel.User where user_type='Marketer'")
    List<User> getAllMarketers();

}
