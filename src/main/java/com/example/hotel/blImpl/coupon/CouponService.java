package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategyI;
import com.example.hotel.bl.coupon.CouponServiceI;
import com.example.hotel.data.coupon.CouponMapper;
import com.example.hotel.enums.CouponType;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CouponService implements CouponServiceI {

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

    @Override
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

    @Override
    public List<Coupon> getByHotel(Integer hotelId) {
        return couponMapper.selectByHotelId(hotelId);
    }

    @Override
    public void addCoupon(Coupon coupon) {
        couponMapper.insertCoupon(coupon);
    }

    @Override
    public List<Coupon> getByType(CouponType couponType) {
        return couponMapper.selectByType(couponType.toString());
    }

    @Override
    public void remove(int id) {
        couponMapper.remove(id);
    }

    @Override
    public List<Coupon> getGlobal() {
        return couponMapper.getGlobal();
    }

}
