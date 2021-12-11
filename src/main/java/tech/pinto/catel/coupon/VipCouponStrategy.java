package tech.pinto.catel.coupon;

import tech.pinto.catel.user.AccountMapper;
import tech.pinto.catel.user.PersonMapper;
import tech.pinto.catel.enums.CouponType;
import tech.pinto.catel.domain.Coupon;
import tech.pinto.catel.domain.Order;
import tech.pinto.catel.domain.User;
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
        User user = order.getUser();
        return user.getVipEnd().isAfter(LocalDateTime.now()) && user.getVipType().compareTo(coupon.getLevel()) >= 0;
    }

}
