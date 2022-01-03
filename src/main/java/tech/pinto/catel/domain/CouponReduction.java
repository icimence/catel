package tech.pinto.catel.domain;

import lombok.Getter;
import lombok.Setter;
import tech.pinto.catel.coupon.dto.DtoCouponRelated;
import tech.pinto.catel.util.UtilRandom;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class CouponReduction extends CouponBase {
    private BigDecimal priceTarget;

    public static CouponReduction random(int lower, int upper, int scale) {
        var r = new CouponReduction();
        r.priceTarget = new BigDecimal(scale * (7 + UtilRandom.ofInt(-3, 4)));
        r.randomize(lower, upper);
        return r;
    }

    @Override
    public String condition() {
        return "订单额度满 " + priceTarget.toString() + " 元可用";
    }

    @Override
    public boolean judge(DtoCouponRelated related) {
        return related.getPrice().compareTo(priceTarget) >= 0;
    }

    @Override
    public String name() {
        return "Reduction";
    }
}
