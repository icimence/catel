package tech.pinto.catel.order.dto;

import lombok.Data;
import tech.pinto.catel.coupon.dto.DtoUsableCoupon;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DtoRefPreview {
    private BigDecimal totalPrice;
    private BigDecimal discountTotal;
    private BigDecimal actualPrice;
    private List<DtoUsableCoupon> availableCoupons;

    public DtoRefPreview(BigDecimal totalPrice, List<DtoUsableCoupon> availableCoupons, BigDecimal discountTotal) {
        this.totalPrice = totalPrice;
        this.discountTotal = discountTotal;
        this.actualPrice = totalPrice.subtract(discountTotal);
        this.availableCoupons = availableCoupons;
    }
}
