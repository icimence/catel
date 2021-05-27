package com.example.hotel.data.hotel;

import com.example.hotel.po.Hotel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface HotelMapper {

    @Insert(value = "insert into hotel.Hotel(name, description, address, biz_region, hotel_star, phone_number, rate, manager_id,credit_bound, pic) " +
            "values (#{hotel.name} ,#{hotel.description} ,#{hotel.address} ,#{hotel.bizRegion} ," +
            "#{hotel.hotelStar} ,#{hotel.phoneNumber} ,#{hotel.rate} ,#{hotel.managerId},#{hotel.creditBound} ," +
            " #{hotel.pic}  )")
    void insertHotel(@Param("hotel") Hotel hotel);

    @Update(value = "update hotel.Hotel set name = #{hotel.name} ,description=#{hotel.description} ,address=#{hotel.address} ," +
            "biz_region=#{hotel.bizRegion} ,hotel_star=#{hotel.hotelStar} ,phone_number=#{hotel.phoneNumber} ," +
            "rate=#{hotel.rate} ,manager_id=#{hotel.managerId} ,pic=#{hotel.pic},announcement=#{hotel.announcement}," +
            "credit_bound=#{hotel.creditBound}   where id=#{hotel.id}  ")
    void updateHotel(@Param("hotel") Hotel hotel);

    @Select(value = "select * from hotel.Hotel")
    List<Hotel> selectAllHotel();

    @Select(value = "select * from hotel.Hotel where id=#{id} ")
    Hotel selectById(@Param("id") Integer id);

    @Select(value = "select * from hotel.Hotel where manager_id=#{id} ")
    List<Hotel> selectByManager(@Param("id") Integer id);

    @Select(value = "select * from hotel.Hotel where manager_id=-1")
    List<Hotel> unboundHotel();

}
