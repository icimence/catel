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

    @Select(value = "select * from hotel.Person where user_id=#{userId}  ")
    List<Resident> selectByUserId(@Param("userId") int userId);

    @Select(value = "select * from hotel.Person where id =#{id} ")
    Resident select(@Param("id") int id);

    @Select(value = "select user_id from hotel.Person where id=#{id} ")
    int getUserId(@Param("id") int id);

}
