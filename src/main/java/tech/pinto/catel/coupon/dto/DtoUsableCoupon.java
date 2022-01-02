package tech.pinto.catel.coupon.dto;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
public class DtoUsableCoupon implements Comparable<DtoUsableCoupon> {
    private long id;
    private boolean available = false;
    private BigDecimal amount;
    private String name;
    private String condition;
    private String outdatedWhen;
    private String type;

    @Override
    public int compareTo(@NonNull DtoUsableCoupon o) {
        if (available && !o.available) return -1;
        else if (!available && o.available) return 1;
        return -amount.compareTo(o.getAmount());
    }
}
