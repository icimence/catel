package tech.pinto.catel.coupon;

import tech.pinto.catel.bl.CouponMatchStrategyI;
import tech.pinto.catel.enums.CouponType;
import tech.pinto.catel.model.Coupon;
import tech.pinto.catel.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CouponService {

    private static final List<CouponMatchStrategyI> strategyList = new ArrayList<>();
    private final TargetMoneyCouponStrategy targetMoneyCouponStrategy;
    private final TimeCouponStrategy timeCouponStrategy;
    private final BirthdayCouponStrategy birthdayCouponStrategy;
    private final MultiRoomCouponStrategy multiRoomCouponStrategy;
    private final CouponMapper couponMapper;

    @Autowired
    public CouponService(TargetMoneyCouponStrategy targetMoneyCouponStrategy,
                         TimeCouponStrategy timeCouponStrategy,
                         BirthdayCouponStrategy birthdayCouponStrategy,
                         MultiRoomCouponStrategy multiRoomCouponStrategy,
                         CouponMapper couponMapper) {
        this.birthdayCouponStrategy = birthdayCouponStrategy;
        this.multiRoomCouponStrategy = multiRoomCouponStrategy;
        this.couponMapper = couponMapper;
        this.targetMoneyCouponStrategy = targetMoneyCouponStrategy;
        this.timeCouponStrategy = timeCouponStrategy;
        strategyList.add(targetMoneyCouponStrategy);
        strategyList.add(timeCouponStrategy);
        strategyList.add(multiRoomCouponStrategy);
        strategyList.add(birthdayCouponStrategy);
    }

    public List<Coupon> getMatchOrderCoupon(Order order) {
        List<Coupon> hotelCoupons = getByHotel(order.getHotelId());
        hotelCoupons.addAll(couponMapper.getGlobal());
        List<Coupon> availAbleCoupons = new ArrayList<>();
        for (Coupon hotelCoupon : hotelCoupons) {
            for (CouponMatchStrategyI strategy : strategyList) {
                if (strategy.isMatch(order, hotelCoupon)) {
                    availAbleCoupons.add(hotelCoupon);
                }
            }
        }
        return availAbleCoupons;
    }

    public List<Coupon> getByHotel(long hotelId) {
        return couponMapper.selectByHotelId(hotelId);
    }

    public void addCoupon(Coupon coupon) {
        couponMapper.insertCoupon(coupon);
    }

    public List<Coupon> getByType(CouponType couponType) {
        return couponMapper.selectByType(couponType.toString());
    }

    public void remove(int id) {
        couponMapper.remove(id);
    }

    public List<Coupon> getGlobal() {
        return couponMapper.getGlobal();
    }

}
