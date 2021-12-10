package tech.pinto.catel.order;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.hibernate.SessionFactory;
import tech.pinto.catel.domain.Order;
import tech.pinto.catel.domain.OrderRoom;
import tech.pinto.catel.domain.RoomConfig;
import tech.pinto.catel.hotel.HotelService;
import tech.pinto.catel.hotel.MapperHotel;
import tech.pinto.catel.hotel.RepoHotel;
import tech.pinto.catel.order.dto.DtoOrderBrief;
import tech.pinto.catel.room.*;
import tech.pinto.catel.user.AccountMapper;
import tech.pinto.catel.order.dto.DtoOrderDetail;
import tech.pinto.catel.room.dto.DtoRoomInfo;
import tech.pinto.catel.enums.OrderState;
import tech.pinto.catel.domain.Hotel;
import tech.pinto.catel.order.dto.DtoReserveGroup;
import tech.pinto.catel.order.dto.DtoReservePersonal;
import tech.pinto.catel.user.AccountService;
import tech.pinto.catel.user.RepoUser;
import tech.pinto.catel.util.MapX;
import tech.pinto.catel.util.OopsException;
import tech.pinto.catel.util.UtilDebug;
import tech.pinto.catel.vo.CreditTransVO;
import tech.pinto.catel.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final MapperOrder mapperOrder;
    private final MapperRoom mapperRoom;
    private final MapperHotel mapperHotel;
    private final HotelService hotelService;
    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final MapperOrderRoom mapperOrderRoom;
    private final MapperRoomConfig mapperRoomConfig;
    private final RepoOrder repoOrder;
    private final RepoOrderRoom repoOrderRoom;
    private final RepoRoom repoRoom;
    private final RepoRoomUnit repoRoomUnit;
    private final RepoRoomConfig repoRoomConfig;
    private final EntityManagerFactory entityManagerFactory;
    private final MapX mapX;

    @Autowired
    public OrderService(MapperOrder mapperOrder, HotelService hotelService, MapperRoom mapperRoom, MapperHotel mapperHotel, AccountService accountService, AccountMapper accountMapper, MapperOrderRoom mapperOrderRoom, MapperRoomConfig mapperRoomConfig, RepoOrder repoOrder, RepoOrderRoom repoOrderRoom, RepoOrderRoom repoOrderRoom1, RepoRoom repoRoom, RepoRoomUnit repoRoomUnit, MapX mapX, RepoHotel repoHotel, RepoUser repoUser, RepoRoomConfig repoRoomConfig, EntityManagerFactory entityManagerFactory) {
        this.mapperOrder = mapperOrder;
        this.hotelService = hotelService;
        this.mapperRoom = mapperRoom;
        this.mapperHotel = mapperHotel;
        this.accountService = accountService;
        this.accountMapper = accountMapper;
        this.mapperOrderRoom = mapperOrderRoom;
        this.mapperRoomConfig = mapperRoomConfig;
        this.repoOrder = repoOrder;
        this.repoOrderRoom = repoOrderRoom1;
        this.repoRoom = repoRoom;
        this.repoRoomUnit = repoRoomUnit;
        this.mapX = mapX;
        this.repoRoomConfig = repoRoomConfig;
        this.entityManagerFactory = entityManagerFactory;
    }

    private boolean roomAvailable(long configId, LocalDate start, LocalDate end, int needed) {
        var roomNumbers = repoRoomUnit.getRoomNumber(configId, start, end);
        return roomNumbers.stream().map(n -> n >= needed).reduce((a, b) -> a && b).orElse(true);
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

        if (!roomAvailable(
            configId, order.getCheckInDate(), order.getCheckOutDate(), order.getRoomNum()
        )) throw new OopsException(2);

        BigDecimal unitPrice = config.getDefPrice();
        BigDecimal price = BigDecimal.valueOf(order.getRoomNum()).multiply(unitPrice);
        order.setPrice(price);
        if (price.compareTo(BigDecimal.ZERO) < 0) throw new OopsException(8);

        // TODO: Using coupons

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

        accountService.creditPunish(order);
        repoRoomUnit.restoreCanceledRoom(order);
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

        List<DtoRoomInfo> roomInfos = order
            .getRooms()
            .stream().map(orderRoom -> {
                System.out.println(orderRoom);
                RoomConfig config = orderRoom.getRoomConfig();
                DtoRoomInfo roomInfo = mapX.toRoomInfo(config);
                roomInfo.setResidentId(orderRoom.getResidentId());
                roomInfo.setRoomId(orderRoom.getRoomId());
                return roomInfo;
            }).collect(Collectors.toList());
        
        dtoOrderDetail.setRoomInfos(roomInfos);
        System.out.println(dtoOrderDetail);
        return dtoOrderDetail;
    }
}
