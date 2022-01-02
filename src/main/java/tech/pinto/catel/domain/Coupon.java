package tech.pinto.catel.domain;

import lombok.Getter;
import lombok.Setter;
import tech.pinto.catel.enums.CouponStatus;
import tech.pinto.catel.enums.CouponType;
import tech.pinto.catel.enums.VipType;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Coupon {

    @Id
    private Long id;
    private CouponStatus status;
    private String name;
    private String description;
    private int hotelId;
    
    private double discount;
    private double discountMoney;
    private double targetMoney;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private CouponType type;
    
    private VipType level;
    private int multiRoomTarget;

    public Coupon() {
        name = "Coupon";
        hotelId = -1;
        status = CouponStatus.Available;
        discount = 1;
        description = "";
        targetMoney = 500;
        discountMoney = 100;
        multiRoomTarget = (int) 1e8;
        startTime = LocalDateTime.now();
        endTime = startTime.plusDays(30);
        level = VipType.Nil;
    }

}
