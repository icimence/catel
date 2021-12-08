package tech.pinto.catel.vo.coupon;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

@Data
@JsonTypeName(value = "Target")
public class TargetCouponVO extends CouponVO {

    private Integer targetMoney;

}
