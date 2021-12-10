package tech.pinto.catel.coupon;

import tech.pinto.catel.bl.CouponMatchStrategyI;
import tech.pinto.catel.user.PersonMapper;
import tech.pinto.catel.domain.Coupon;
import tech.pinto.catel.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BirthdayCouponStrategy implements CouponMatchStrategyI {

    private final PersonMapper personMapper;

    @Autowired
    public BirthdayCouponStrategy(PersonMapper personMapper) {
        this.personMapper = personMapper;
    }

    @Override
    public boolean isMatch(Order order, Coupon coupon) {
        // TODO: disabled temporary
        return false;
//        if (coupon.getType() != CouponType.Birthday) return false;
//        Person person = personMapper.select(order.getPersonId());
//        LocalDate in = order.getCheckInDate();
//        LocalDate out = order.getCheckOutDate();
//        LocalDate birthday = person.getBirthday();
//        LocalDate curBir1 = birthday.plusYears(in.getYear() - birthday.getYear());
//        LocalDate curBir2 = birthday.plusYears(out.getYear() - birthday.getYear());
//        return (curBir1.isAfter(in) || curBir1.equals(in)) && curBir1.isBefore(out)
//                || (curBir2.isAfter(in) || curBir2.equals(in)) && curBir2.isBefore(out);
    }

}
