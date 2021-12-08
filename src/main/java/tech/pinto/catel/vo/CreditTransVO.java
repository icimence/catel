package tech.pinto.catel.vo;

import tech.pinto.catel.enums.OrderState;
import lombok.Data;

@Data
public class CreditTransVO {

    private Integer id;
    private String hotelName;
    private OrderState orderState;
    private Double creditDelta;

}
