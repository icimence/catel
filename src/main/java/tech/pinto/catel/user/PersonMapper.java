package tech.pinto.catel.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import tech.pinto.catel.domain.Resident;

import java.util.List;

@Mapper
public interface PersonMapper extends BaseMapper<Resident> {

    @Insert(value = "insert into Person (user_id, id_no, real_name, phone_number, birthday) values " +
        "(#{userId} ,#{idNo} ,#{realName} ,#{phoneNumber} ,#{birthday})")
    void add(Resident resident);

    @Delete(value = "delete from hotel.Person where id=#{id} ")
    void delete(@Param("id") int id);

    @Update(value = "update hotel.Person set user_id=#{person.userId} ,real_name=#{person.realName} ," +
        "id_no=#{person.idNo} ,phone_number=#{person.phoneNumber} ,birthday=#{person.birthday} where id=#{person.id}  ")
    void update(@Param("resident") Resident resident);

    @Select(value = "select * from hotel.Person where user_id=#{userId}  ")
    List<Resident> selectByUserId(@Param("userId") int userId);

    @Select(value = "select * from hotel.Person where id =#{id} ")
    Resident select(@Param("id") int id);

    @Select(value = "select user_id from hotel.Person where id=#{id} ")
    int getUserId(@Param("id") int id);

}
