package tech.pinto.catel.domain;

import lombok.*;
import org.hibernate.Hibernate;
import tech.pinto.catel.enums.RoomType;

import javax.persistence.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class RoomConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_room_config_id")
    private Long id;
    @ManyToOne
    private Hotel hotel;
    private String name;
    private int peopleMax;
    private boolean breakfast;
    private RoomType type;
    private BigDecimal defPrice;
    private int roomNumber = 0;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RoomConfig that = (RoomConfig) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public RoomConfig(Hotel hotel, String name, int peopleMax, boolean breakfast, RoomType type, BigDecimal defPrice, int roomNumber) {
        this.hotel = hotel;
        this.name = name;
        this.peopleMax = peopleMax;
        this.breakfast = breakfast;
        this.type = type;
        this.defPrice = defPrice;
        this.roomNumber = roomNumber;
    }
}
