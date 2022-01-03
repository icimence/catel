package tech.pinto.catel.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.pinto.catel.domain.Order;

import java.util.List;

public interface RepoOrder extends JpaRepository<Order, Long> {
    @Query("select o from OrderList o " +
        "where o.orderState = :#{T(tech.pinto.catel.order.OrderState).Available} " +
        "and o.checkOutDate <= current_date ")
    List<Order> outdated();
    
    List<Order> findByUserId(long user_id);
}
