package tech.pinto.catel.coupon;

import tech.pinto.catel.domain.Coupon;
import tech.pinto.catel.domain.Order;

public interface CouponMatchStrategyI {

    /**
     * 检测优惠券对某一订单是否适用
     */
    boolean isMatch(Order order, Coupon coupon);

}
