package tech.pinto.catel.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.pinto.catel.enums.OrderState;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("OrderList")
public class Order {

    private Long id;
    private long userId;
    private long hotelId;
    private int roomNum;
    private double creditDelta;
    private BigDecimal price;
    private String hotelName = null;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    @TableField("create_date")
    private LocalDateTime createdTime = LocalDateTime.now();
    private OrderState orderState = OrderState.Available;

}
