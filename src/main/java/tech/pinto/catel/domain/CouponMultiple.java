package tech.pinto.catel.domain;

import lombok.Getter;
import lombok.Setter;
import tech.pinto.catel.coupon.dto.DtoCouponRelated;
import tech.pinto.catel.util.UtilRandom;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class CouponMultiple extends CouponBase {
    private int numberTarget;

    public static CouponMultiple random() {
        var r = new CouponMultiple();
        r.numberTarget = UtilRandom.ofInt(2, 8);
        r.randomize();
        return r;
    }

    @Override
    public String condition() {
        return "订购满 " + numberTarget + " 间可用。";
    }

    @Override
    public boolean judge(DtoCouponRelated related) {
        return related.getRoomNum() >= numberTarget;
    }
}
