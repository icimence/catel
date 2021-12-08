package tech.pinto.catel.vo.coupon;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

@Data
@JsonTypeName(value = "Time")
public class TimeCouponVO extends CouponVO {

    private String startTime;
    private String endTime;

}
