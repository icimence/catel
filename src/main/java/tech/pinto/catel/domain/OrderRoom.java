package tech.pinto.catel.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@IdClass(OrderRoom._Id.class)
public class OrderRoom {
    @Id
    @ManyToOne
    private Order order;
    @Id
    private Long residentId;
    private Long roomId;
    @ManyToOne
    private RoomConfig roomConfig;

    static public class _Id implements Serializable {
        // TODO specify index order
        private Order order;
        private Long residentId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
            _Id id = (_Id) o;
            return order != null && Objects.equals(order, id.order)
                && residentId != null && Objects.equals(residentId, id.residentId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(order, residentId);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderRoom orderRoom = (OrderRoom) o;
        return order != null && Objects.equals(order, orderRoom.order)
            && residentId != null && Objects.equals(residentId, orderRoom.residentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, residentId);
    }
}
