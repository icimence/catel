package tech.pinto.catel.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import tech.pinto.catel.comment.dto.DtoComment;
import tech.pinto.catel.comment.dto.DtoPublishComment;
import tech.pinto.catel.domain.Comment;
import tech.pinto.catel.domain.Order;
import tech.pinto.catel.order.dto.DtoReserve;
import tech.pinto.catel.order.dto.DtoReservePersonal;

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
}
