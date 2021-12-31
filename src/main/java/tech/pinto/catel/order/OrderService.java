package tech.pinto.catel.order;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import tech.pinto.catel.domain.*;
import tech.pinto.catel.hotel.HotelService;
import tech.pinto.catel.order.dto.*;
import tech.pinto.catel.room.*;
import tech.pinto.catel.room.dto.DtoRoomEntry;
import tech.pinto.catel.user.AccountMapper;
import tech.pinto.catel.enums.OrderState;
import tech.pinto.catel.user.AccountService;
import tech.pinto.catel.util.MapX;
import tech.pinto.catel.util.OopsException;
import tech.pinto.catel.vo.CreditTransVO;
import tech.pinto.catel.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OrderService {

    private final HotelService hotelService;
    private final AccountService accountService;
    private final MapperOrder mapperOrder;
    private final AccountMapper accountMapper;
    private final RepoOrder repoOrder;
    private final RepoRoomUnit repoRoomUnit;
    private final RepoRoomConfig repoRoomConfig;
    private final MapX mapX;

    @Autowired
    public OrderService(MapperOrder mapperOrder, HotelService hotelService, AccountService accountService, AccountMapper accountMapper, RepoOrder repoOrder, RepoRoomUnit repoRoomUnit, MapX mapX, RepoRoomConfig repoRoomConfig) {
        this.mapperOrder = mapperOrder;
        this.hotelService = hotelService;
        this.accountService = accountService;
        this.accountMapper = accountMapper;
        this.repoOrder = repoOrder;
        this.repoRoomUnit = repoRoomUnit;
        this.repoRoomConfig = repoRoomConfig;
        this.mapX = mapX;
    }

    private BigDecimal pricePerRoom(List<RoomUnit> units) {
        return units
            .stream()
            .map(RoomUnit::getPrice)
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);
    }

    public DtoRefPreview refPreview(DtoReservePersonal dtoReservePersonal) {
        var order = mapX.toPersonOrder(dtoReservePersonal);
        var configId = dtoReservePersonal.getConfigId();
        var units = repoRoomUnit.relatedUnits(configId, order.getCheckInDate(), order.getCheckOutDate());
        var pricePerRoom = pricePerRoom(units);
        var totalPrice = pricePerRoom.multiply(BigDecimal.valueOf(order.getRoomNum()));
        return new DtoRefPreview(totalPrice);
    }

    public long reserve(DtoReservePersonal dtoReservePersonal) throws OopsException {
        // Initialize model
        var order = mapX.toPersonOrder(dtoReservePersonal);

        // Check user's credit
        double curCredit = order.getUser().getCredit();
        int bound = order.getHotel().getCreditBound();
        if (curCredit < bound) {
            throw new OopsException(5);
        }

        // Make sure rooms available
        var configId = dtoReservePersonal.getConfigId();
        var config = repoRoomConfig.getById(configId);

        var units = repoRoomUnit.relatedUnits(configId, order.getCheckInDate(), order.getCheckOutDate());
        var available = units.stream().map(u -> u.getNumber() >= order.getRoomNum()).reduce((a, b) -> a && b).orElse(true);
        if (!available) throw new OopsException(2);

        var price = refPreview(dtoReservePersonal).getTotalPrice();
        order.setPrice(price);

        // TODO: Using coupons
        if (price.compareTo(BigDecimal.ZERO) < 0) throw new OopsException(8);

        // Update to create order, update room number, 
        // TODO invalid used coupons

        for (long residentId : dtoReservePersonal.getResidents()) {
            OrderRoom orderRoom = new OrderRoom();
            orderRoom.setOrder(order);
            orderRoom.setRoomConfig(config);
            orderRoom.setResidentId(residentId);
            order.getRooms().add(orderRoom);
        }

        repoRoomUnit.invalidOccupied(config, order.getRoomNum(), order.getCheckInDate(), order.getCheckOutDate());

        repoOrder.save(order);

        return order.getId();
    }

    public void reserveForGroup(DtoReserveGroup dtoReserveGroup) {
    }

    private OrderVO convert(Order order) {
        OrderVO orderVO = new OrderVO();
        BeanUtil.copyProperties(order, orderVO, CopyOptions.create().ignoreNullValue());
        var hotel = order.getHotel();
        orderVO.setHotelName(hotel.getName());
        orderVO.setHotelAddress(hotel.getAddress());
        return orderVO;
    }

    public List<OrderVO> getAllOrders() {
        List<Order> orders = mapperOrder.getAllOrders();
        return orders.stream().map(this::convert).collect(Collectors.toList());
    }

    public List<DtoOrderBrief> getUserOrders(int userId) {
        return repoOrder
            .findByUserId(userId)
            .stream().map(mapX::toBrief)
            .collect(Collectors.toList());
    }

    @Transactional
    public void annulOrder(long orderId) {
        var order = repoOrder.getById(orderId);
        order.setOrderState(OrderState.Canceled);
        var config = order.getRooms().get(0).getRoomConfig();

        accountService.creditPunish(order);
        repoRoomUnit.restoreCanceledRoom(config, order.getRoomNum(), order.getCheckInDate(), order.getCheckOutDate());
        repoOrder.save(order);
    }

    public List<Order> getHotelOrders(long hotelId) {
        List<Order> orders = mapperOrder.getAllOrders();
        return orders.stream().filter(order -> order.getHotel().getId() == hotelId).collect(Collectors.toList());
    }

    public List<Order> getOrderByManager(Integer managerId) {
        List<Hotel> hotels = hotelService.getHotelByManager(managerId);
        List<Order> orders = new ArrayList<>();
        for (Hotel hotel : hotels) {
            orders.addAll(mapperOrder.getOrderByHotel(hotel.getId()));
        }
        return orders;
    }

    public List<CreditTransVO> getCreditTransaction(int userId) {
        List<CreditTransVO> normal = mapperOrder.getUserOrders(userId).stream().filter(o -> o.getCreditDelta() != 0).map(o -> {
            CreditTransVO creditTransVO = new CreditTransVO();
            BeanUtil.copyProperties(o, creditTransVO, CopyOptions.create().ignoreNullValue());
            creditTransVO.setHotelName(o.getHotel().getName());
            return creditTransVO;
        }).collect(Collectors.toList());
        accountMapper.getDirect(userId).forEach(o -> {
            CreditTransVO creditTransVO = new CreditTransVO();
            creditTransVO.setCreditDelta(o.getCreditDelta());
            creditTransVO.setHotelName("充值");
            creditTransVO.setId(-1);
            creditTransVO.setOrderState(OrderState.Direct);
            normal.add(creditTransVO);
        });
        return normal;
    }

    public void expire(int orderId) {
        mapperOrder.expire(orderId);
    }

    public List<Order> available(int id) {
        return mapperOrder.getAvailable(id);
    }

    public DtoOrderDetail orderDetail(long orderId) {
        Order order = repoOrder.getById(orderId);
        DtoOrderDetail dtoOrderDetail = mapX.toDetail(order);

        List<DtoRoomEntry> roomInfos = order
            .getRooms()
            .stream().map(orderRoom -> {
                RoomConfig config = orderRoom.getRoomConfig();
                DtoRoomEntry roomInfo = mapX.toRoomInfo(config);
                roomInfo.setResidentId(orderRoom.getResidentId());
                roomInfo.setRoomId(orderRoom.getRoomId());
                return roomInfo;
            }).collect(Collectors.toList());

        dtoOrderDetail.setRoomInfos(roomInfos);
        return dtoOrderDetail;
    }
}
