package com.example.hotel.blImpl.order;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.example.hotel.bl.user.AccountServiceI;
import com.example.hotel.blImpl.hotel.HotelService;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.data.user.PersonMapper;
import com.example.hotel.dto.*;
import com.example.hotel.enums.OrderState;
import com.example.hotel.model.Hotel;
import com.example.hotel.model.Order;
import com.example.hotel.model.Room;
import com.example.hotel.util.AllMapper;
import com.example.hotel.util.OopsException;
import com.example.hotel.vo.CreditTransVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.hotel.HotelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderMapper orderMapper;
    private final RoomMapper roomMapper;
    private final PersonMapper personMapper;
    private final HotelMapper hotelMapper;
    private final HotelService hotelService;
    private final AccountServiceI accountServiceI;
    private final AccountMapper accountMapper;

    @Autowired
    public OrderService(OrderMapper orderMapper, HotelService hotelService, AccountServiceI accountServiceI, RoomMapper roomMapper, PersonMapper personMapper, HotelMapper hotelMapper, AccountMapper accountMapper) {
        this.orderMapper = orderMapper;
        this.hotelService = hotelService;
        this.accountServiceI = accountServiceI;
        this.roomMapper = roomMapper;
        this.personMapper = personMapper;
        this.hotelMapper = hotelMapper;
        this.accountMapper = accountMapper;
    }

    private boolean roomAvailable(long roomId, int number, long start, long end) {
        long today = LocalDate.now().toEpochDay();
        int s = (int) (start - today);
        int t = (int) (end - today);
        List<Integer> roomResidual = roomMapper.getRoomNumber(roomId);
        for (int i = s; i >= 0 && i < t; i++) {
            if (roomResidual.get(i) < number) return false;
        }
        return true;
    }

    public long addOrder(DtoReservePersonal dtoReservePersonal) throws OopsException {
        // Initialize model
        Order order = AllMapper.X.toOrder(dtoReservePersonal);
        Hotel hotel = hotelMapper.selectById(order.getHotelId());
        order.setHotelName(hotel.getName());
        int roomNumber = dtoReservePersonal.getResidents().size();
        order.setRoomNum(roomNumber);

        // Check user's credit
        double curCredit = accountMapper.getCredit(dtoReservePersonal.getUserId());
        int bound = hotelMapper.selectById(dtoReservePersonal.getHotelId()).getCreditBound();
        if (curCredit < bound) {
            throw new OopsException(5);
        }

        // Make sure rooms available
        long roomId = dtoReservePersonal.getRoomId();
        if (!roomAvailable(roomId, order.getRoomNum(),
                           order.getCheckInDate().toEpochDay(),
                           order.getCheckOutDate().toEpochDay())
        ) throw new OopsException(2);

        // TODO: Calculate price
        BigDecimal unitPrice = roomMapper.getPrice(roomId);
        BigDecimal price = BigDecimal.valueOf(roomNumber).multiply(unitPrice);
        order.setPrice(price);
        if (price.compareTo(BigDecimal.ZERO) < 0) throw new OopsException(8);
        // TODO: Using coupons

        // Update to create order, update room number, invalid used coupon
        orderMapper.insert(order);
        roomMapper.updateRoomInfo(order, roomId);
        
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
        List<Order> orders = orderMapper.getAllOrders();
        return orders.stream().map(this::convert).collect(Collectors.toList());
    }

    public List<OrderVO> getUserOrders(int userId) {
        return orderMapper.getUserOrders(userId)
                          .stream().map(this::convert)
                          .collect(Collectors.toList());
    }

    public void annulOrder(int orderId) {
        Order order = orderMapper.selectById(orderId);
        // TODO: get roomId direct from order, now wrong
        long roomId = order.getHotelId();
        accountServiceI.creditDown(order);
        roomMapper.updateRoomInfo(order, roomId);
        order.setOrderState(OrderState.Canceled);
        orderMapper.update(order);
    }

    public List<Order> getHotelOrders(Integer hotelId) {
        List<Order> orders = orderMapper.getAllOrders();
        return orders.stream().filter(order -> order.getHotelId() == hotelId).collect(Collectors.toList());
    }

    public List<Order> getOrderByManager(Integer managerId) {
        List<Hotel> hotels = hotelService.getHotelByManager(managerId);
        List<Order> orders = new ArrayList<>();
        for (Hotel hotel : hotels) {
            orders.addAll(orderMapper.getOrderByHotel(hotel.getId()));
        }
        return orders;
    }

    public List<CreditTransVO> getCreditTransaction(int userId) {
        List<CreditTransVO> normal = orderMapper.getUserOrders(userId).stream().filter(o -> o.getCreditDelta() != 0).map(o -> {
            CreditTransVO creditTransVO = new CreditTransVO();
            BeanUtil.copyProperties(o, creditTransVO, CopyOptions.create().ignoreNullValue());
            creditTransVO.setHotelName(hotelMapper.selectById(o.getHotelId()).getName());
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
        orderMapper.expire(orderId);
    }

    public List<Order> available(int id) {
        return orderMapper.getAvailable(id);
    }

    public DtoOrderDetail orderDetail(long orderId) {
        Order order = orderMapper.selectById(orderId);
        DtoOrderDetail dtoOrderDetail = AllMapper.X.toDetail(order);
        dtoOrderDetail.setRoomInfo(null);
        System.out.println(dtoOrderDetail);
        return dtoOrderDetail;
    }
}
