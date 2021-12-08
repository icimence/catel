package tech.pinto.catel.vo.coupon;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

@Data
@JsonTypeName(value = "Birthday")
public class BirthdayCouponVO extends CouponVO {}
