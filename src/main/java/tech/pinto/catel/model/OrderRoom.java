package tech.pinto.catel.model;

import lombok.Data;

@Data
public class OrderRoom {
    long orderId;
    long roomId;
    long configId;
    long residentId;
}
