package tech.pinto.catel.coupon;

import tech.pinto.catel.domain.CouponBase;
import tech.pinto.catel.domain.Order;

public interface CouponMatchStrategyI {

    /**
     * 检测优惠券对某一订单是否适用
     */
    boolean isMatch(Order order, CouponBase coupon);

}
