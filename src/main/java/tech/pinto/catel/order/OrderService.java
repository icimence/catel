package tech.pinto.catel.order;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import tech.pinto.catel.bl.AccountServiceI;
import tech.pinto.catel.hotel.HotelService;
import tech.pinto.catel.hotel.MapperHotel;
import tech.pinto.catel.room.MapperRoom;
import tech.pinto.catel.user.AccountMapper;
import tech.pinto.catel.order.dto.DtoOrderDetail;
import tech.pinto.catel.room.dto.DtoRoomInfo;
import tech.pinto.catel.enums.OrderState;
import tech.pinto.catel.hotel.Hotel;
import tech.pinto.catel.model.Order;
import tech.pinto.catel.model.OrderRoom;
import tech.pinto.catel.order.dto.DtoReserveGroup;
import tech.pinto.catel.order.dto.DtoReservePersonal;
import tech.pinto.catel.room.Room;
import tech.pinto.catel.util.AllMapper;
import tech.pinto.catel.util.OopsException;
import tech.pinto.catel.vo.CreditTransVO;
import tech.pinto.catel.vo.OrderVO;
import tech.pinto.catel.vo.hotel.HotelVO;
import tech.pinto.catel.room.MapperOrderRoom;
import tech.pinto.catel.room.MapperRoomConfig;
import tech.pinto.catel.room.RoomConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private final AccountServiceI accountServiceI;
    private final AccountMapper accountMapper;
    private final MapperOrderRoom mapperOrderRoom;
    private final MapperRoomConfig mapperRoomConfig;

    @Autowired
    public OrderService(MapperOrder mapperOrder, HotelService hotelService, AccountServiceI accountServiceI, MapperRoom mapperRoom, MapperHotel mapperHotel, AccountMapper accountMapper, MapperOrderRoom mapperOrderRoom, MapperRoomConfig mapperRoomConfig) {
        this.mapperOrder = mapperOrder;
        this.hotelService = hotelService;
        this.accountServiceI = accountServiceI;
        this.mapperRoom = mapperRoom;
        this.mapperHotel = mapperHotel;
        this.accountMapper = accountMapper;
        this.mapperOrderRoom = mapperOrderRoom;
        this.mapperRoomConfig = mapperRoomConfig;
    }

    private boolean roomAvailable(long roomId, int number, long start, long end) {
        long today = LocalDate.now().toEpochDay();
        int s = (int) (start - today);
        int t = (int) (end - today);
        List<Integer> roomResidual = mapperRoom.getRoomNumber(roomId);
        for (int i = s; i >= 0 && i < t; i++) {
            if (roomResidual.get(i) < number) return false;
        }
        return true;
    }

    public long addOrder(DtoReservePersonal dtoReservePersonal) throws OopsException {
        // Initialize model
        Order order = AllMapper.X.toOrder(dtoReservePersonal);
        Hotel hotel = mapperHotel.selectById(order.getHotelId());
        order.setHotelName(hotel.getName());
        int roomNumber = dtoReservePersonal.getResidents().size();
        order.setRoomNum(roomNumber);

        // Check user's credit
        double curCredit = accountMapper.getCredit(dtoReservePersonal.getUserId());
        int bound = mapperHotel.selectById(dtoReservePersonal.getHotelId()).getCreditBound();
        if (curCredit < bound) {
            throw new OopsException(5);
        }

        // Make sure rooms available
        long roomId = dtoReservePersonal.getRoomId();
        Room room = mapperRoom.selectById(roomId);
        RoomConfig config = room.getRoomConfig();

        if (!roomAvailable(roomId, order.getRoomNum(),
                           order.getCheckInDate().toEpochDay(),
                           order.getCheckOutDate().toEpochDay())
        ) throw new OopsException(2);

        BigDecimal unitPrice = config.getDefPrice();
        BigDecimal price = BigDecimal.valueOf(roomNumber).multiply(unitPrice);
        order.setPrice(price);
        if (price.compareTo(BigDecimal.ZERO) < 0) throw new OopsException(8);
        // TODO: Using coupons

        // Update to create order, update room number, TODO invalid used coupon
        mapperOrder.insert(order);

        System.out.println(order);
        OrderRoom orderRoom = new OrderRoom();
        orderRoom.setOrderId(order.getId());
        orderRoom.setRoomId(roomId);
        orderRoom.setConfigId(room.getRoomConfig().getId());

        for (long residentId : dtoReservePersonal.getResidents()) {
            orderRoom.setResidentId(residentId);
            mapperOrderRoom.insert(orderRoom);
        }
        mapperRoom.updateRoomInfo(order, room.getRoomConfig().getId());
        return order.getId();
    }

    public void addGroupOrder(DtoReserveGroup dtoReserveGroup) {
    }

    private OrderVO convert(Order order) {
        OrderVO orderVO = new OrderVO();
        BeanUtil.copyProperties(order, orderVO, CopyOptions.create().ignoreNullValue());
        HotelVO hotel = hotelService.selectById(order.getHotelId());
        orderVO.setHotelName(hotel.getName());
        orderVO.setHotelAddress(hotel.getAddress());
        return orderVO;
    }

    public List<OrderVO> getAllOrders() {
        List<Order> orders = mapperOrder.getAllOrders();
        return orders.stream().map(this::convert).collect(Collectors.toList());
    }

    public List<OrderVO> getUserOrders(int userId) {
        return mapperOrder.getUserOrders(userId)
                          .stream().map(this::convert)
                          .collect(Collectors.toList());
    }

    public void annulOrder(int orderId) {
        Order order = mapperOrder.selectById(orderId);
        // TODO: get roomId direct from order, now wrong
        long roomId = order.getHotelId();
        accountServiceI.creditDown(order);
        mapperRoom.updateRoomInfo(order, roomId);
        order.setOrderState(OrderState.Canceled);
        mapperOrder.update(order);
    }

    public List<Order> getHotelOrders(Integer hotelId) {
        List<Order> orders = mapperOrder.getAllOrders();
        return orders.stream().filter(order -> order.getHotelId() == hotelId).collect(Collectors.toList());
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
            creditTransVO.setHotelName(mapperHotel.selectById(o.getHotelId()).getName());
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
        Order order = mapperOrder.selectById(orderId);
        DtoOrderDetail dtoOrderDetail = AllMapper.X.toDetail(order);
        LambdaQueryWrapper<OrderRoom> wrapper = new LambdaQueryWrapper<OrderRoom>().eq(OrderRoom::getOrderId, orderId);
        List<OrderRoom> orderRooms = mapperOrderRoom.selectList(wrapper);
        List<DtoRoomInfo> roomInfos = orderRooms.stream().map(orderRoom -> {
            RoomConfig config = mapperRoomConfig.selectById(orderRoom.getConfigId());
            DtoRoomInfo roomInfo = AllMapper.X.toRoomInfo(config);
            roomInfo.setResidentId(orderRoom.getResidentId());
            roomInfo.setRoomId(orderRoom.getRoomId());
            return roomInfo;
        }).collect(Collectors.toList());
        dtoOrderDetail.setRoomInfos(roomInfos);
        System.out.println(dtoOrderDetail);
        return dtoOrderDetail;
    }
}
