package tech.pinto.catel.room;

import lombok.*;
import org.hibernate.Hibernate;
import tech.pinto.catel.hotel.Hotel;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_room_id")
    private Long id;
    
    private String roomNo;
    @ManyToOne
    private Hotel hotel;
    @ManyToOne
    private RoomConfig roomConfig;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Room room = (Room) o;
        return id != null && Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public Room(String roomNo, Hotel hotel, RoomConfig roomConfig) {
        this.roomNo = roomNo;
        this.hotel = hotel;
        this.roomConfig = roomConfig;
    }
}
