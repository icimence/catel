package tech.pinto.catel.bl;

import tech.pinto.catel.model.Coupon;
import tech.pinto.catel.model.Order;

public interface CouponMatchStrategyI {

    /**
     * 检测优惠券对某一订单是否适用
     */
    boolean isMatch(Order order, Coupon coupon);

}
