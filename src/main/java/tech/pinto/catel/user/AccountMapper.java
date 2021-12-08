package tech.pinto.catel.user;

import tech.pinto.catel.model.CreditUp;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountMapper {

    @Select(value = "select * from hotel.User where email=#{email} ")
    User getAccountByEmail(@Param("email") String email);

    @Select(value = "select * from hotel.User where id=#{id}")
    User select(@Param("id") long id);

    @Select(value = "select * from hotel.User where username=#{username}  ")
    User getAccountByUsername(@Param("username") String username);

    @Insert(value = "insert into hotel.User(email, password, username,credit, user_type, avatar,vip_end,vip_type) values " +
            "(#{user.email} ,#{user.password} ,#{user.username} ,#{user.credit} ," +
            "#{user.userType} ,#{user.avatar}, #{user.vipEnd} ,#{user.vipType}  )")
    void createNewAccount(@Param("user") User user);

    @Insert(value = "insert into hotel.CreditUp (user_id, credit_delta) values (#{creditUp.userId} ,#{creditUp.creditDelta} )")
    void creditUp(@Param("creditUp") CreditUp creditUp);

    @Update(value = "update hotel.User set password=#{user.password} ,username=#{user.username} ," +
            "avatar=#{user.avatar},vip_end=#{user.vipEnd},vip_type=#{user.vipType} where id=#{user.id} ")
    void updateAccount(@Param("user") User user);

    @Update(value = "update hotel.User set vip_type=0 where vip_end<now()")
    void vipExpire();

    @Delete(value = "delete from hotel.Person where user_id=#{id} ")
    void beforeDelete(@Param("id") int id);

    @Select(value = "select coalesce(sum(credit_delta), 0) from hotel.OrderList where user_id=#{id} ")
    double getCreditFromOrder(@Param("id") long id);

    @Select(value = "select coalesce(sum(credit_delta), 0) from hotel.CreditUp where user_id=#{id} ")
    double getCreditFromDirect(@Param("id") long id);

    @Select(value = "select * from hotel.CreditUp where user_id=#{id} ")
    List<CreditUp> getDirect(@Param("id") long id);

    @Select("select credit from User where id=#{userId}")
    double getCredit(long userId);
}