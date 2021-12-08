package tech.pinto.catel.coupon;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import tech.pinto.catel.hotel.MapperHotel;
import tech.pinto.catel.user.PersonMapper;
import tech.pinto.catel.model.Coupon;
import tech.pinto.catel.model.Order;
import tech.pinto.catel.util.Response;
import tech.pinto.catel.vo.coupon.CouponVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {

    private final CouponService couponService;
    private final PersonMapper personMapper;
    private final MapperHotel mapperHotel;

    @Autowired
    public CouponController(CouponService couponService, PersonMapper personMapper, MapperHotel mapperHotel) {
        this.couponService = couponService;
        this.personMapper = personMapper;
        this.mapperHotel = mapperHotel;
    }

    /**
     * create a coupon with given type and required fields
     * _in hotel id, description, name,discount price and ratio
     * _in if time limited coupon -> starting and ending time
     * _in if multi-room coupon -> how much room needed
     * _in if target money coupon -> the target
     */
    @PostMapping("/new")
    public Response addCoupon(@RequestBody CouponVO couponVO) {
        Coupon coupon = new Coupon();
        BeanUtil.copyProperties(couponVO, coupon, CopyOptions.create().ignoreNullValue());
        couponService.addCoupon(coupon);
        return Response.buildSuccess().setMessage(5);
    }

    /**
     * remove existed coupon by id
     * _in coupon id
     */
    @PostMapping("/rm")
    public Response remove(@RequestParam int id) {
        couponService.remove(id);
        return Response.buildSuccess().setMessage(6);
    }

    /**
     * with partial info of an order (without price and some status)
     * get all available coupons for it
     * _in partially finished order
     * _out list of available coupons
     */
    @PostMapping("/get-matched")
    public Response getOrderMatchCoupons(@RequestBody Order order) {
        order.setHotelName(mapperHotel.selectById(order.getHotelId()).getName());
        List<Coupon> coupons = couponService.getMatchOrderCoupon(order);
        return Response.buildSuccess(coupons);
    }

    /**
     * get coupons by hotel id
     * _in hotel id
     * _out list of available coupons in hotel and those global
     */
    @GetMapping("/by-hotel")
    public Response getAllByHotel(@RequestParam Integer hotelId) {
        List<Coupon> coupons = couponService.getByHotel(hotelId);
        return Response.buildSuccess(coupons);
    }

    /**
     * get all global coupons
     * _out list of global coupons
     */
    @GetMapping("/global")
    @Deprecated
    public Response getGlobalCoupons() {
        return Response.buildSuccess(couponService.getGlobal());
    }

}
