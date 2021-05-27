package com.example.hotel.data.user;

import com.example.hotel.po.Person;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PersonMapper {

    @Insert(value = "insert into hotel.Person (user_id, real_name, id_no, phone_number, birthday) values " +
            "(#{person.userId} ,#{person.realName} ,#{person.idNo} ,#{person.phoneNumber} ,#{person.birthday} )")
    void insert(@Param("person") Person person);

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
