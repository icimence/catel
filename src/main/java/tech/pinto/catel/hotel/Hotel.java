package tech.pinto.catel.hotel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import tech.pinto.catel.enums.BizRegion;
import tech.pinto.catel.enums.HotelStar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_hotel_id")
    private Long id;
    private String name;
    private String address;
    private BizRegion bizRegion;
    private HotelStar hotelStar;
    private Double rate;
    private String description;
    private String phoneNumber;
    private Integer managerId;
    private String pic;
    private int creditBound;
    private String announcement;
    private BigDecimal minPrice = null;

    public Hotel(String name, String address, BizRegion bizRegion, HotelStar hotelStar, String description, String phoneNumber, String pic, int creditBound, String announcement) {
        this.name = name;
        this.address = address;
        this.bizRegion = bizRegion;
        this.hotelStar = hotelStar;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.pic = pic;
        this.creditBound = creditBound;
        this.announcement = announcement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Hotel hotel = (Hotel) o;
        return id != null && Objects.equals(id, hotel.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
