package tech.pinto.catel.domain;

import lombok.Getter;
import lombok.Setter;
import tech.pinto.catel.coupon.dto.DtoCouponRelated;
import tech.pinto.catel.enums.VipLevel;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class CouponVip extends CouponBase {
    private VipLevel vipLevel;

    @Override
    public String condition() {
        return "成为 " + vipLevel + " Vip 用户即可使用！";
    }

    @Override
    public boolean judge(DtoCouponRelated related) {
        return related.getVipLevel().compareTo(vipLevel) >= 0;
    }

    @Override
    public String name() {
        return "Vip";
    }
}
