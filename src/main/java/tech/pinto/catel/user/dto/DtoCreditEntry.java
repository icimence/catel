package tech.pinto.catel.user.dto;

import lombok.Data;

@Data
public class DtoCreditEntry {
    private double creditDelta;
    private String createdWhen;
    private long orderId;
}
