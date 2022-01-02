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

    public static CouponReduction random() {
        var r = new CouponReduction();
        r.priceTarget = new BigDecimal(UtilRandom.ofInt(5, 15) * 100);
        r.randomize();
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
