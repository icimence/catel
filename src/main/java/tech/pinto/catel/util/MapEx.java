package tech.pinto.catel.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import tech.pinto.catel.comment.dto.DtoComment;
import tech.pinto.catel.comment.dto.DtoPublishComment;
import tech.pinto.catel.coupon.dto.DtoCouponRelated;
import tech.pinto.catel.coupon.dto.DtoUsableCoupon;
import tech.pinto.catel.domain.Comment;
import tech.pinto.catel.domain.CouponBase;
import tech.pinto.catel.domain.Hotel;
import tech.pinto.catel.domain.Order;
import tech.pinto.catel.hotel.BizRegion;
import tech.pinto.catel.hotel.QueryParam;
import tech.pinto.catel.hotel.dto.DtoHotelBrief;
import tech.pinto.catel.hotel.dto.DtoHotelDetail;
import tech.pinto.catel.hotel.dto.DtoHotelQuery;
import tech.pinto.catel.order.dto.DtoReserve;
import tech.pinto.catel.order.dto.DtoReservePersonal;
import tech.pinto.catel.util.error.EnumOutRange;

public abstract class MapEx extends MapX {
    @Autowired
    @Qualifier("delegate")
    private MapX mapX;

    @Override
    public Order toOrder(DtoReserve src) {
        Order order = mapX.toOrder(src);
        var name = order.getHotel().getName();
        order.setHotelName(name);
        return order;
    }

    @Override
    public Order toPersonOrder(DtoReservePersonal src) {
        var order = toOrder(src);
        var roomNumber = src.getResidents() != null ? src.getResidents().size() : 0;
        order.setRoomNum(roomNumber);
        return order;
    }

    @Override
    public Comment toComment(DtoPublishComment src) {
        var comment = mapX.toComment(src);
        var order = comment.getOrder();
        comment.setUser(order.getUser());
        comment.setHotel(order.getHotel());
        return comment;
    }

    @Override
    public DtoComment toDtoComment(Comment src) {
        var dto = mapX.toDtoComment(src);
        var user = src.getUser();
        dto.setAvatar(user.getAvatar());
        dto.setUsername(user.getUsername());
        return dto;
    }

    @Override
    public DtoHotelBrief toBrief(Hotel src) {
        var dto = mapX.toBrief(src);
        dto.setRate(src.getHotelStat().getRate());
        return dto;
    }

    @Override
    public DtoHotelDetail toDetail(Hotel hotel) {
        var dto = mapX.toDetail(hotel);
        var stat = hotel.getHotelStat();
        var dist = new double[]{
            stat.getScore1(),
            stat.getScore2(),
            stat.getScore3(),
            stat.getScore4(),
            stat.getScore5(),
        };
        for (int i = 0; i < dist.length; i++) {
            dist[i] /= stat.getTotal();
        }
        dto.setRateDist(dist);
        dto.setRate(stat.getRate());
        return dto;
    }

    @Override
    public QueryParam toQueryParam(DtoHotelQuery src) {
        var param = mapX.toQueryParam(src);
        var filter = param.getFilter();

        var starStr = src.getFilterStars();

        if (starStr != null) {
            var n = starStr.length();
            var stars = new int[n];
            for (int i = 0; i < n; i++) {
                stars[i] = Character.digit(starStr.charAt(i), 10);
            }
            filter.setStars(stars);
        }

        try {
            var bizRegion = BizRegion.from(src.getFilterLocation());
            filter.setRegion(bizRegion);
        } catch (EnumOutRange e) {
            filter.setRegion(null);
            filter.setName(src.getFilterLocation());
        }

        return param;
    }

    @Override
    public DtoUsableCoupon toUsableCoupon(CouponBase couponBase, DtoCouponRelated related) {
        var dto = mapX.toUsableCoupon(couponBase, related);
        var condition = couponBase.condition();
        var available = couponBase.judge(related);
        dto.setAvailable(available);
        dto.setCondition(condition);
        dto.setType(couponBase.name());
        return dto;
    }
}
