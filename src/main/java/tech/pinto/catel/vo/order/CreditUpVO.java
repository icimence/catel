package tech.pinto.catel.vo.order;

import lombok.Data;

@Data
public class CreditUpVO {

    private Integer id;
    private String username;
    private String email;
    private Double creditDelta;

}
