package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategyI;
import com.example.hotel.data.user.PersonMapper;
import com.example.hotel.enums.CouponType;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.Order;
import com.example.hotel.po.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BirthdayCouponStrategy implements CouponMatchStrategyI {

    private final PersonMapper personMapper;

    @Autowired
    public BirthdayCouponStrategy(PersonMapper personMapper) {
        this.personMapper = personMapper;
    }

    @Override
    public boolean isMatch(Order order, Coupon coupon) {
        if (coupon.getType() != CouponType.Birthday) return false;
        Person person = personMapper.select(order.getPersonId());
        LocalDate in = order.getCheckInDate();
        LocalDate out = order.getCheckOutDate();
        LocalDate birthday = person.getBirthday();
        LocalDate curBir1 = birthday.plusYears(in.getYear() - birthday.getYear());
        LocalDate curBir2 = birthday.plusYears(out.getYear() - birthday.getYear());
        return (curBir1.isAfter(in) || curBir1.equals(in)) && curBir1.isBefore(out)
                || (curBir2.isAfter(in) || curBir2.equals(in)) && curBir2.isBefore(out);
    }

}
