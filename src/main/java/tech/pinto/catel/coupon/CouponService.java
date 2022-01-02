package tech.pinto.catel.coupon;

import tech.pinto.catel.coupon.dto.DtoCouponRelated;
import tech.pinto.catel.coupon.dto.DtoUsableCoupon;
import tech.pinto.catel.enums.CouponType;
import tech.pinto.catel.domain.CouponBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.pinto.catel.util.MapX;
import tech.pinto.catel.util.error.OopsException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponService {

    private final CouponMapper couponMapper;
    private final RepoCoupon repoCoupon;
    private final MapX mapX;

    @Autowired
    public CouponService(CouponMapper couponMapper, RepoCoupon repoCoupon, MapX mapX) {
        this.couponMapper = couponMapper;
        this.repoCoupon = repoCoupon;
        this.mapX = mapX;
    }

    public List<CouponBase> getByHotel(long hotelId) {
        return couponMapper.selectByHotelId(hotelId);
    }

    public void addCoupon(CouponBase coupon) {
        couponMapper.insertCoupon(coupon);
    }

    public List<CouponBase> getByType(CouponType couponType) {
        return couponMapper.selectByType(couponType.toString());
    }

    public void remove(int id) {
        couponMapper.remove(id);
    }

    public List<CouponBase> getGlobal() {
        return couponMapper.getGlobal();
    }

    public List<DtoUsableCoupon> getUsable(DtoCouponRelated related, long userId, long hotelId) {
        var coupons = repoCoupon.getUsable(userId, hotelId);
        return coupons
            .stream()
            .map(coupon -> mapX.toUsableCoupon(coupon, related))
            .sorted()
            .collect(Collectors.toList());
    }

    public BigDecimal checkAndSum(DtoCouponRelated related, List<Long> couponIds) throws OopsException {
        var coupons = repoCoupon.findAllById(couponIds);
        var error = false;
        for (var coupon : coupons) {
            if (!coupon.judge(related)) error = true;
        }
        if (error) throw new OopsException(13);
        return coupons.stream().map(CouponBase::getDiscountAmount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }
}
