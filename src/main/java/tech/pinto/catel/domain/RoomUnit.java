package tech.pinto.catel.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class RoomUnit {
    @EmbeddedId
    private _Id id;
    private BigDecimal price;
    private int number;

    @Embeddable
    @Getter
    @Setter
    public static class _Id implements Serializable {
        @ManyToOne
        @MapsId
        private RoomConfig roomConfig;
        private LocalDate date;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
            _Id id = (_Id) o;
            return roomConfig != null && Objects.equals(roomConfig, id.roomConfig)
                && date != null && Objects.equals(date, id.date);
        }

        @Override
        public int hashCode() {
            return Objects.hash(roomConfig, date);
        }

        public _Id() {}

        public _Id(RoomConfig roomConfig, LocalDate date) {
            this.roomConfig = roomConfig;
            this.date = date;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RoomUnit roomUnit = (RoomUnit) o;
        return id != null && Objects.equals(id, roomUnit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public RoomUnit(RoomConfig roomConfig, LocalDate date, BigDecimal price, int number) {
        this.id = new _Id(roomConfig, date);
        this.price = price;
        this.number = number;
    }

}
