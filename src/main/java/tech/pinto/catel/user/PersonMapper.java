package tech.pinto.catel.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import tech.pinto.catel.model.Person;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PersonMapper extends BaseMapper<Person> {

    @Insert(value = "insert into Person (user_id, id_no, real_name, phone_number, birthday) values " +
        "(#{userId} ,#{idNo} ,#{realName} ,#{phoneNumber} ,#{birthday})")
    void add(Person person);

    @Delete(value = "delete from hotel.Person where id=#{id} ")
    void delete(@Param("id") int id);

    @Update(value = "update hotel.Person set user_id=#{person.userId} ,real_name=#{person.realName} ," +
        "id_no=#{person.idNo} ,phone_number=#{person.phoneNumber} ,birthday=#{person.birthday} where id=#{person.id}  ")
    void update(@Param("person") Person person);

    @Select(value = "select * from hotel.Person where user_id=#{userId}  ")
    List<Person> selectByUserId(@Param("userId") int userId);

    @Select(value = "select * from hotel.Person where id =#{id} ")
    Person select(@Param("id") int id);

    @Select(value = "select user_id from hotel.Person where id=#{id} ")
    int getUserId(@Param("id") int id);

}
