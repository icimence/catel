package tech.pinto.catel.room;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import tech.pinto.catel.domain.Room;
import tech.pinto.catel.domain.Order;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface MapperRoom extends BaseMapper<Room> {

    @Insert(value = "insert into hotel.Room (price, total, hotel_id, room_type,breakfast,people_max) values " +
        "(#{room.price},#{room.total} ,#{room.hotelId} ," +
        "#{room.roomType},#{room.breakfast} ,#{room.peopleMax}  )")
    @Options(useGeneratedKeys = true, keyProperty = "room.id", keyColumn = "id")
    void insertRoom(@Param("room") Room room);

    @Select(value = "select * from hotel.Room where hotel_id=#{hotelId} ")
    List<Room> selectRoomsByHotelId(@Param("hotelId") long hotelId);

    @Insert(value = "insert into hotel.RoomNumber (room_id, number, date) values " +
        "(#{room.id}, #{room.total}, date_add(now(), interval 0 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 1 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 2 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 3 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 4 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 5 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 6 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 7 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 8 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 9 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 10 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 11 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 12 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 13 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 14 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 15 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 16 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 17 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 18 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 19 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 20 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 21 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 22 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 23 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 24 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 25 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 26 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 27 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 28 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 29 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 30 day))," +
        "(#{room.id}, #{room.total}, date_add(now(), interval 31 day));")
    void initRoomNumber(@Param("room") Room room);

    @Select(value = "select number from hotel.RoomNumber where room_id=#{id} order by date ")
    List<Integer> getRoomNumber(@Param("id") long roomId);

    @Update(value = "update hotel.RoomNumber x set x.date=date_add(x.date,interval 32 day), number=#{room.total} where " +
        "x.date=curdate() and room_id=#{room.id} ")
    void updateRoomNumber(@Param("room") Room room);

    @Select(value = "select * from hotel.Room")
    List<Room> getAll();

    @Update(value = "delete from hotel.Room where id=#{id} ")
    void removeRoom(@Param("id") int id);

    @Update(value = "delete from hotel.RoomNumber where room_id=#{id} ")
    void removeRoomNumber(@Param("id") int id);

    @Select(value = "select count(*)>0 from hotel.Room where room_type=#{room.roomType} and breakfast=#{room.breakfast} and hotel_id=#{room.hotelId} ")
    boolean exists(@Param("room") Room room);

}
