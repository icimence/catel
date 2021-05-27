package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategyI;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.data.user.PersonMapper;
import com.example.hotel.enums.CouponType;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.Order;
import com.example.hotel.po.Person;
import com.example.hotel.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VipCouponStrategy implements CouponMatchStrategyI {

    final private AccountMapper accountMapper;
    final private PersonMapper personMapper;

    @Autowired
    public VipCouponStrategy(AccountMapper accountMapper, PersonMapper personMapper) {
        this.accountMapper = accountMapper;
        this.personMapper = personMapper;
    }

    @Override
    public boolean isMatch(Order order, Coupon coupon) {
        if (coupon.getType() != CouponType.Vip) return false;
        Person person = personMapper.select(order.getPersonId());
        User user = accountMapper.select(person.getUserId());
        return user.getVipEnd().isAfter(LocalDateTime.now()) && user.getVipType().compareTo(coupon.getLevel()) >= 0;
    }

}