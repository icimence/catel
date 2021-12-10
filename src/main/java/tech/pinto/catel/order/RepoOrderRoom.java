package tech.pinto.catel.order;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.pinto.catel.domain.Order;
import tech.pinto.catel.domain.OrderRoom;

import java.util.List;

public interface RepoOrderRoom extends JpaRepository<OrderRoom, OrderRoom._Id> {
    List<OrderRoom> findByOrder(Order order);
}
