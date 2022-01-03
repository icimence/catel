package tech.pinto.catel.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.hibernate.Hibernate;
import tech.pinto.catel.order.OrderState;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@TableName("order_list")
@Table(name = "order_list")
@Entity(name = "OrderList")
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_order_id")
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Hotel hotel;
    private int roomNum;
    private double creditDelta;
    private BigDecimal price;
    private String hotelName;
    private String hotelAddress;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    @TableField("create_date")
    private LocalDateTime createdTime = LocalDateTime.now();
    private OrderState orderState = OrderState.Available;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<OrderRoom> rooms = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order order = (Order) o;
        return id != null && Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
