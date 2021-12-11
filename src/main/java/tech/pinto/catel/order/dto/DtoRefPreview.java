package tech.pinto.catel.order.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class DtoRefPreview {
    private BigDecimal totalPrice;
    private List<Long> availableCoupon = new ArrayList<>();

    public DtoRefPreview(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
