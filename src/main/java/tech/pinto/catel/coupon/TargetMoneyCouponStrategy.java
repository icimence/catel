package tech.pinto.catel.coupon;

import tech.pinto.catel.bl.CouponMatchStrategyI;
import tech.pinto.catel.enums.CouponType;
import tech.pinto.catel.model.Coupon;
import tech.pinto.catel.model.Order;
import org.springframework.stereotype.Service;

@Service
public class TargetMoneyCouponStrategy implements CouponMatchStrategyI {

    @Override
    public boolean isMatch(Order order, Coupon coupon) {
        if (coupon.getType() != CouponType.Target) return false;
        return order.getPrice().doubleValue() >= coupon.getTargetMoney();
    }

}
