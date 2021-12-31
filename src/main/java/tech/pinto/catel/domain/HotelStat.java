package tech.pinto.catel.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class HotelStat {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Hotel hotel;
    private double rate;
    private int score1;
    private int score2;
    private int score3;
    private int score4;
    private int score5;
    private int total;

    public HotelStat(Hotel hotel) {
        this.hotel = hotel;
    }

}
