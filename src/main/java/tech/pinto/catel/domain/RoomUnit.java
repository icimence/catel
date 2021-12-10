package tech.pinto.catel.domain;

import lombok.*;
import tech.pinto.catel.domain.RoomConfig;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class RoomUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_room_unit_id")
    private Long id;
    @ManyToOne
    private RoomConfig roomConfig;
    private LocalDate date;
    private BigDecimal price;
    private int number;

    public RoomUnit(RoomConfig roomConfig, LocalDate date, BigDecimal price, int number) {
        this.roomConfig = roomConfig;
        this.date = date;
        this.price = price;
        this.number = number;
    }
}
