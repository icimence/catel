package tech.pinto.catel.coupon;

import tech.pinto.catel.domain.Coupon;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CouponMapper {

    @Insert(value = "insert into hotel.Coupon (description,hotel_id, type, name, target_money, discount, status, start_time, end_time, discount_money,multi_room_target) " +
            "values (#{coupon.description} ,#{coupon.hotelId} ,#{coupon.type} ,#{coupon.name} ,#{coupon.targetMoney} ," +
            "#{coupon.discount} ,#{coupon.status} ,#{coupon.startTime} ,#{coupon.endTime} ,#{coupon.discountMoney},#{coupon.multiRoomTarget}  )")
    void insertCoupon(@Param("coupon") Coupon coupon);

    @Select(value = "select * from hotel.Coupon where hotel_id=#{hotelId} ")
    List<Coupon> selectByHotelId(@Param("hotelId") long hotelId);

    @Select(value = "select * from hotel.Coupon where type=#{type} ")
    List<Coupon> selectByType(@Param("type") String type);

    @Delete(value = "delete from hotel.Coupon where id=#{id}  ")
    void remove(@Param("id") long id);

    @Select(value = "select * from hotel.Coupon where hotel_id=-1")
    List<Coupon> getGlobal();

}
