package tech.pinto.catel.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.pinto.catel.coupon.dto.DtoCouponRelated;
import tech.pinto.catel.enums.CouponStatus;
import tech.pinto.catel.util.UtilRandom;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Inheritance
@Getter
@Setter
@NoArgsConstructor
abstract public class CouponBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_coupon_id")
    private Long id;
    @ManyToOne
    private Hotel hotel;
    @ManyToOne
    private User owner;

    private CouponStatus status = CouponStatus.Available;
    private String name = "默认优惠券";
    private String description = "默认描述";
    private BigDecimal discountAmount;
    private LocalDate timeEnabled;
    private LocalDate timeOutdated;

    protected void randomize() {
        timeEnabled = UtilRandom.ofDate(1990);
        timeOutdated = UtilRandom.ofDate(2015);
        discountAmount = new BigDecimal(UtilRandom.ofInt(100, 200));
    }

    public abstract String condition();

    public abstract boolean judge(DtoCouponRelated related);

}
