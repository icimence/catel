package tech.pinto.catel.user.dto;

import lombok.Data;

@Data
public class DtoCreditEntry implements Comparable<DtoCreditEntry> {
    private double creditDelta;
    private String createdWhen;
    private long orderId;

    @Override
    public int compareTo(DtoCreditEntry o) {
        return -createdWhen.compareTo(o.createdWhen);
    }
}
