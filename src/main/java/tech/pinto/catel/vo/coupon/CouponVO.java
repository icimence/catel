package tech.pinto.catel.vo.coupon;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import tech.pinto.catel.coupon.CouponStatus;
import tech.pinto.catel.coupon.CouponType;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TimeCouponVO.class, name = "Time"),
        @JsonSubTypes.Type(value = TargetCouponVO.class, name = "Target"),
        @JsonSubTypes.Type(value = BirthdayCouponVO.class, name = "Birthday"),
        @JsonSubTypes.Type(value = MultiRoomCouponVO.class, name = "MultiRoom"),
        @JsonSubTypes.Type(value = VipCouponVO.class, name = "Vip"),
        @JsonSubTypes.Type(value = GlobalCouponVO.class, name = "Global")
})
public abstract class CouponVO {

    private Integer id;
    private Integer hotelId;
    private String description;
    private CouponStatus status;
    private String name;
    private CouponType type;
    private Double discount;
    private Integer discountMoney;

}
