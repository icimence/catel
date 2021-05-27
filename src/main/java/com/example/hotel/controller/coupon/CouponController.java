package com.example.hotel.controller.coupon;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.example.hotel.bl.coupon.CouponServiceI;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.user.PersonMapper;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.Order;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.coupon.CouponVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {

    private final CouponServiceI couponService;
    private final PersonMapper personMapper;
    private final HotelMapper hotelMapper;

    @Autowired
    public CouponController(CouponServiceI couponService, PersonMapper personMapper, HotelMapper hotelMapper) {
        this.couponService = couponService;
        this.personMapper = personMapper;
        this.hotelMapper = hotelMapper;
    }

    /**
     * create a coupon with given type and required fields
     * _in hotel id, description, name,discount price and ratio
     * _in if time limited coupon -> starting and ending time
     * _in if multi-room coupon -> how much room needed
     * _in if target money coupon -> the target
     */
    @PostMapping("/new")
    public ResponseVO addCoupon(@RequestBody CouponVO couponVO) {
        Coupon coupon = new Coupon();
        BeanUtil.copyProperties(couponVO, coupon, CopyOptions.create().ignoreNullValue());
        couponService.addCoupon(coupon);
        return ResponseVO.buildSuccess().setMessage(5);
    }

    /**
     * remove existed coupon by id
     * _in coupon id
     */
    @PostMapping("/rm")
    public ResponseVO remove(@RequestParam int id) {
        couponService.remove(id);
        return ResponseVO.buildSuccess().setMessage(6);
    }

    /**
     * with partial info of an order (without price and some status)
     * get all available coupons for it
     * _in partially finished order
     * _out list of available coupons
     */
    @PostMapping("/get-matched")
    public ResponseVO getOrderMatchCoupons(@RequestBody Order order) {
        order.setUserId(personMapper.getUserId(order.getPersonId()));
        order.setHotelName(hotelMapper.selectById(order.getHotelId()).getName());

        List<Coupon> coupons = couponService.getMatchOrderCoupon(order);
        return ResponseVO.buildSuccess(coupons);
    }

    /**
     * get coupons by hotel id
     * _in hotel id
     * _out list of available coupons in hotel and those global
     */
    @GetMapping("/by-hotel")
    public ResponseVO getAllByHotel(@RequestParam Integer hotelId) {
        List<Coupon> coupons = couponService.getByHotel(hotelId);
        return ResponseVO.buildSuccess(coupons);
    }


    /**
     * get all global coupons
     * _out list of global coupons
     */
    @GetMapping("/global")
    @Deprecated
    public ResponseVO getGlobalCoupons() {
        return ResponseVO.buildSuccess(couponService.getGlobal());
    }

}
