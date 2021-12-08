package tech.pinto.catel.vo.coupon;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import tech.pinto.catel.model.Coupon;
import tech.pinto.catel.enums.CouponStatus;
import tech.pinto.catel.enums.CouponType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

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

    public Coupon persist() {
        Coupon coupon = new Coupon();
        BeanUtil.copyProperties(this, coupon, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        return coupon;
    }

}
