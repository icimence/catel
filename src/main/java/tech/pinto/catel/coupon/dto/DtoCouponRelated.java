package tech.pinto.catel.coupon.dto;

import lombok.Data;
import tech.pinto.catel.user.VipLevel;

import java.math.BigDecimal;

@Data
public class DtoCouponRelated {
    private VipLevel vipLevel;
    private int roomNum;
    private BigDecimal price;

    public DtoCouponRelated(VipLevel vipLevel, int roomNum, BigDecimal price) {
        this.vipLevel = vipLevel;
        this.roomNum = roomNum;
        this.price = price;
    }
}
